package microservices.rental

import fish.payara.cloud.connectors.kafka.api.KafkaListener
import fish.payara.cloud.connectors.kafka.api.OnRecord
import org.apache.kafka.clients.consumer.ConsumerRecord
import javax.ejb.ActivationConfigProperty
import javax.ejb.MessageDriven


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
    ), ActivationConfigProperty(propertyName = "autoCommitInterval", propertyValue = "100"), ActivationConfigProperty(
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
    @OnRecord(topics = ["testing"])
    open fun getMessageTest(record: ConsumerRecord<*, *>) {
        println("Got record on topic testing $record")
    }
}

