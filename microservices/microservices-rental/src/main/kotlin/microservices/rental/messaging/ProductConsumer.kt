package microservices.rental.messaging

import core.domain.common.valueobjects.AccessionNumber
import core.domain.rent.Product
import fish.payara.cloud.connectors.kafka.api.KafkaListener
import fish.payara.cloud.connectors.kafka.api.OnRecord
import microservices.rental.dto.ProductDto
import microservices.rental.mappers.toDomain
import org.apache.kafka.clients.consumer.ConsumerRecord
import repositories.rental.adapters.ProductRepositoryAdapter
import javax.ejb.ActivationConfigProperty
import javax.ejb.MessageDriven
import javax.inject.Inject

@MessageDriven(
    activationConfig = [
        ActivationConfigProperty(propertyName = "clientId", propertyValue = "testClient"),
        ActivationConfigProperty(propertyName = "groupIdConfig", propertyValue = "test-consumer-group"),
        ActivationConfigProperty(propertyName = "topics", propertyValue = "products"),
        ActivationConfigProperty(propertyName = "bootstrapServersConfig", propertyValue = "\${ENV=KAFKA_BROKER}"),
        ActivationConfigProperty(propertyName = "autoCommitInterval", propertyValue = "100"),
        ActivationConfigProperty(propertyName = "retryBackoff", propertyValue = "1000"),
        ActivationConfigProperty(
            propertyName = "keyDeserializer",
            propertyValue = "org.apache.kafka.common.serialization.StringDeserializer"
        ),
        ActivationConfigProperty(
            propertyName = "valueDeserializer",
            propertyValue = "microservices.rental.messaging.deserializers.ProductDeserializer"
        ),
        ActivationConfigProperty(propertyName = "pollInterval", propertyValue = "1000")
    ]
)
open class ProductConsumer : KafkaListener {

    @Inject
    private lateinit var productRepositoryAdapter: ProductRepositoryAdapter

    @OnRecord(topics = ["products"])
    open fun onProductUpdate(record: ConsumerRecord<String, ProductDto>) {
        try {
            if(record.value().deleted){
                productRepositoryAdapter.delete(AccessionNumber(record.value().accessionNumber))
            }else{
                productRepositoryAdapter.save(record.value().toDomain())
            }
        } catch (e: Exception) {
            print(e)
        }
    }
}