package microservices.rental

import com.fasterxml.jackson.databind.ObjectMapper
import core.domain.common.valueobjects.AccessionNumber
import core.domain.common.valueobjects.Email
import core.domain.rent.Client
import core.domain.rent.Product
import fish.payara.cloud.connectors.kafka.api.KafkaListener
import fish.payara.cloud.connectors.kafka.api.OnRecord
import microservices.rental.dto.ClientDto
import org.apache.kafka.clients.consumer.ConsumerRecord
import repositories.rental.adapters.ClientRepositoryAdapter
import repositories.rental.adapters.ProductRepositoryAdapter
import java.lang.Exception
import javax.ejb.ActivationConfigProperty
import javax.ejb.MessageDriven
import javax.inject.Inject

@MessageDriven(
    activationConfig = [ActivationConfigProperty(
        propertyName = "clientId",
        propertyValue = "testClient"
    ), ActivationConfigProperty(
        propertyName = "groupIdConfig",
        propertyValue = "test-consumer-group"
    ), ActivationConfigProperty(
        propertyName = "topics",
        propertyValue = "clients,products"
    ), ActivationConfigProperty(
        propertyName = "bootstrapServersConfig",
        propertyValue = "localhost:29092"
    ), ActivationConfigProperty(propertyName = "autoCommitInterval", propertyValue = "100"),
        ActivationConfigProperty(
        propertyName = "retryBackoff",
        propertyValue = "1000"
    ), ActivationConfigProperty(
        propertyName = "keyDeserializer",
        propertyValue = "org.apache.kafka.common.serialization.StringDeserializer"
    ), ActivationConfigProperty(
        propertyName = "valueDeserializer",
        propertyValue = "microservices.rental.ClientDeserializer"
    ), ActivationConfigProperty(propertyName = "pollInterval", propertyValue = "1000")]
)
open class KafkaMDB : KafkaListener {

    @Inject
    private lateinit var productRepositoryAdapter: ProductRepositoryAdapter

    @Inject
    private lateinit var clientRepositoryAdapter: ClientRepositoryAdapter

    @OnRecord(topics = ["products"])
    open fun onProductUpdate(record: ConsumerRecord<String, String>) {
        try {
//            println("Got record on topic testing $record")
            val mapper = ObjectMapper()
            val map = mapper.readValue(record.value(), Map::class.java)
            val product = Product(AccessionNumber(map["accessionNumber"] as String))
            productRepositoryAdapter.save(product)
        }catch(e: Exception){
            print(e)
        }
    }

    @OnRecord(topics = ["clients"])
    open fun onClientUpdate(record: ConsumerRecord<String, Client>) {
        try {
//            println("Got record on topic testing $record")
//            val mapper = ObjectMapper()
//            val map = mapper.readValue(record.value(), Map::class.java)
//            val email = Email(map["email"] as String)
//            val active = map["active"] as Boolean
//            val client = Client(email, active)
            clientRepositoryAdapter.save(record.value())
        }catch(e: Exception){
            print(e)
        }
    }
}

