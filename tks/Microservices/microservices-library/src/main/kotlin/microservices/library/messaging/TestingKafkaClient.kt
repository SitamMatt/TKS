package microservices.library.messaging

import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.Topic

@KafkaClient
interface TestingKafkaClient {

    @Topic("testing")
    fun sendMessage(@KafkaKey key: String, msg: String)
}