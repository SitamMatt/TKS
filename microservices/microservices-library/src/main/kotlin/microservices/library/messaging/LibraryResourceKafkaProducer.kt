package microservices.library.messaging

import core.domain.resource.Resource
import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.Topic
import io.micronaut.messaging.annotation.Header
import io.reactivex.Single
import microservices.library.dto.LibraryResourceMessageDto


@KafkaClient
interface LibraryResourceKafkaProducer {

    @Topic("products")
    fun sendMessage(@KafkaKey key: String, msg: Single<Resource>, @Header("IsDeleted") isDeleted: Boolean)
}