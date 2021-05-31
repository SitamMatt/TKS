package microservices.user.management

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import core.domain.user.User
import io.quarkus.kafka.client.serialization.ObjectMapperSerializer

class ClientSerializer : ObjectMapperSerializer<User>() {

    override fun serialize(arg0: String?, arg1: User?): ByteArray? {
        var retVal: ByteArray? = null
        val objectMapper = ObjectMapper().registerModule(KotlinModule())
        try {
            retVal = objectMapper.writeValueAsString(arg1)
                .encodeToByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return retVal
    }
}