package microservices.rental

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import core.domain.common.valueobjects.Email
import core.domain.rent.Client
import microservices.rental.dto.ClientDto
import org.apache.kafka.common.serialization.Deserializer

class ClientDeserializer : Deserializer<Client> {
    override fun close() = Unit

    override fun configure(p0: MutableMap<String, *>?, p1: Boolean) = Unit

    override fun deserialize(p0: String?, p1: ByteArray?): Client {
        try {
            val mapper = ObjectMapper().registerModule(KotlinModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            val value = mapper.readValue(p1, ClientDto::class.java)
            val gg = Client(Email(value.email!!), value.active!!)
            return gg
        }catch (e: Exception){
            print(e)
            throw e
        }
    }
}