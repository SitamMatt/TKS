package microservices.rental

import fish.payara.cloud.connectors.kafka.api.KafkaConnectionFactory
import org.apache.kafka.clients.producer.ProducerRecord
import java.util.logging.Level
import java.util.logging.Logger
import javax.annotation.Resource
import javax.ejb.Schedule
import javax.ejb.Stateless
import javax.resource.ConnectionFactoryDefinition
import javax.resource.spi.TransactionSupport


@ConnectionFactoryDefinition(
    name = "java:module/env/KafkaConnectionFactory",
    description = "Kafka Connection Factory",
    interfaceName = "fish.payara.cloud.connectors.kafka.KafkaConnectionFactory",
    resourceAdapter = "kafka-rar-0.5.0",
    minPoolSize = 2,
    maxPoolSize = 2,
    transactionSupport = TransactionSupport.TransactionSupportLevel.NoTransaction,
    properties = ["bootstrapServersConfig=localhost:29092", "clientId=PayaraMicroMessenger"]
)
@Stateless
open class KafkaMessenger {
    @Resource(lookup = "java:module/env/KafkaConnectionFactory")
    open var factory: KafkaConnectionFactory? = null
//    @Schedule(hour = "*", minute = "*", second = "*/5", persistent = false)
    open fun sendMessage() {
        try {
            factory!!.createConnection().use { conn ->
                conn.send(
                    ProducerRecord<Any?, Any?>(
                        "testing",
                        "Sent from Payara Micro."
                    )
                )
            }
        } catch (ex: Exception) {
            Logger.getLogger(javaClass.name).log(Level.SEVERE, null, ex)
        }
    }
}

