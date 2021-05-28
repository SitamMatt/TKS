package microservices.library.messaging

import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.OffsetReset
import io.micronaut.configuration.kafka.annotation.Topic

@KafkaListener(offsetReset = OffsetReset.LATEST)
open class TestingKafkaListener {

    init{

    }

    @Topic("testing")
    open fun receive(name: String){
        println("Got message - $name")
    }
}