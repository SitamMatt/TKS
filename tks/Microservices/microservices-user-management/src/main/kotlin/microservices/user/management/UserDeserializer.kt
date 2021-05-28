package microservices.user.management

import core.domain.user.User
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer

open class UserDeserializer() : ObjectMapperDeserializer<User>(User::class.java) {

}