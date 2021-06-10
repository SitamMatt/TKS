package microservices.rental.messaging.deserializers

import microservices.rental.Producer
import com.fasterxml.jackson.databind.ObjectMapper
import core.domain.rent.Client
import microservices.rental.dto.ClientDto
import microservices.rental.mappers.toDomain
import org.apache.kafka.common.serialization.Deserializer

class ClientDeserializer : Deserializer<Client> {

    private val mapper: ObjectMapper = Producer().produceJsonMapper()

    override fun close() = Unit

    override fun configure(p0: MutableMap<String, *>?, p1: Boolean) = Unit

    override fun deserialize(topic: String?, data: ByteArray?): Client {
        try {
            val dto = mapper.readValue(data, ClientDto::class.java)
            return dto.toDomain()
        } catch (e: Exception) {
            print(e)
            throw e
        }
    }
}