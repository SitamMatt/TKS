package microservices.library.messaging

import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.Topic
import io.reactivex.Single

class Mess{
    var a = "Hello"
    var b ="World"
}

@KafkaClient
interface TestingKafkaClient {

    @Topic("testing")
    fun sendMessage(@KafkaKey key: String, msg: Single<Mess>)
}