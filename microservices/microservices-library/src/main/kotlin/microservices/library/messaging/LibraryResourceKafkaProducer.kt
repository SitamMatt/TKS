package microservices.library.messaging

import core.domain.resource.Resource
import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.Topic
import io.reactivex.Single


@KafkaClient
interface LibraryResourceKafkaProducer {

//    @Topic("library-resource")
    @Topic("products")
    fun sendMessage(@KafkaKey key: String, msg: Single<Resource>)
}