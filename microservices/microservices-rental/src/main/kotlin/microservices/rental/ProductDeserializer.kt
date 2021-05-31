package microservices.rental

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import core.domain.common.valueobjects.AccessionNumber
import core.domain.common.valueobjects.Email
import core.domain.rent.Client
import core.domain.rent.Product
import microservices.rental.dto.ClientDto
import microservices.rental.dto.ProductDto
import org.apache.kafka.common.serialization.Deserializer

class ProductDeserializer : Deserializer<Product> {
    override fun close() = Unit

    override fun configure(p0: MutableMap<String, *>?, p1: Boolean) = Unit

    override fun deserialize(p0: String?, p1: ByteArray?): Product {
        try {
            val mapper = ObjectMapper().registerModule(KotlinModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            val value = mapper.readValue(p1, ProductDto::class.java)
            val product = Product(AccessionNumber(value.accessionNumber!!))
            return product
        }catch(e: Exception){
            print(e)
            throw e
        }
    }
}