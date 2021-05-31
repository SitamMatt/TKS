package microservices.rental.messaging

import core.domain.rent.Client
import fish.payara.cloud.connectors.kafka.api.KafkaListener
import fish.payara.cloud.connectors.kafka.api.OnRecord
import org.apache.kafka.clients.consumer.ConsumerRecord
import repositories.rental.adapters.ClientRepositoryAdapter
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
            propertyValue = "microservices.rental.messaging.deserializers.ClientDeserializer"
        ), ActivationConfigProperty(propertyName = "pollInterval", propertyValue = "1000")]
)
open class ClientConsumer : KafkaListener {

    @Inject
    private lateinit var clientRepositoryAdapter: ClientRepositoryAdapter

    @OnRecord(topics = ["clients"])
    open fun onClientUpdate(record: ConsumerRecord<String, Client>) {
        try {
            println("Got record on topic testing $record")
            clientRepositoryAdapter.save(record.value())
        } catch (e: Exception) {
            print(e)
        }
    }
}

