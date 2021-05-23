package microservices.rental

import com.fasterxml.jackson.databind.ObjectMapper
import core.domain.common.valueobjects.AccessionNumber
import core.domain.rent.Product
import fish.payara.cloud.connectors.kafka.api.KafkaListener
import fish.payara.cloud.connectors.kafka.api.OnRecord
import org.apache.kafka.clients.consumer.ConsumerRecord
import repositories.rental.adapters.ProductRepositoryAdapter
import java.lang.Exception
import javax.ejb.ActivationConfigProperty
import javax.ejb.MessageDriven
import javax.inject.Inject

class Mess{
    var a = "Hello"
    var b ="World"
}

@MessageDriven(
    activationConfig = [ActivationConfigProperty(
        propertyName = "clientId",
        propertyValue = "testClient"
    ), ActivationConfigProperty(
        propertyName = "groupIdConfig",
        propertyValue = "test-consumer-group"
    ), ActivationConfigProperty(
        propertyName = "topics",
        propertyValue = "testing"
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
        propertyValue = "org.apache.kafka.common.serialization.StringDeserializer"
    ), ActivationConfigProperty(propertyName = "pollInterval", propertyValue = "1000")]
)
open class KafkaMDB : KafkaListener {

    @Inject
    private lateinit var productRepositoryAdapter: ProductRepositoryAdapter

    @OnRecord(topics = ["testing"])
    open fun getMessageTest(record: ConsumerRecord<String, String>) {
        try {
//            println("Got record on topic testing $record")
            val mapper = ObjectMapper()
            val map = mapper.readValue(record.value(), Map::class.java)
            val product = Product(AccessionNumber(map["accessionNumber"] as String))
            productRepositoryAdapter.save(product)
        }catch(e: Exception){
            print(e)
        }

//        val str: String =record.value()
//        val product = mapper.readValue<Product>(str)
    }
}

