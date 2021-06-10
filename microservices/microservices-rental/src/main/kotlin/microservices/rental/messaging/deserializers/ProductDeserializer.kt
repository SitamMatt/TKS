package microservices.rental.messaging.deserializers

import microservices.rental.Producer
import core.domain.rent.Product
import microservices.rental.dto.ProductDto
import microservices.rental.mappers.toDomain
import org.apache.kafka.common.serialization.Deserializer

class ProductDeserializer : Deserializer<ProductDto> {

    private val mapper = Producer().produceJsonMapper()

    override fun close() = Unit

    override fun configure(p0: MutableMap<String, *>?, p1: Boolean) = Unit

    override fun deserialize(p0: String?, p1: ByteArray?): ProductDto? {
        try {
            val dto = mapper.readValue(p1, ProductDto::class.java)
            return dto
        } catch (e: Exception) {
            print(e)
            return null
        }
    }
}