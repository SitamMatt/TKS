package repositories.rental.mappers

import core.domain.common.valueobjects.AccessionNumber
import core.domain.rent.Product
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class ProductMapperTest {
    private val product = Product(AccessionNumber("EEEE-121"))
    private val dto = product.toEntity()
    private val domain = dto.toDomain()


    @Test
    fun productToEntityTest(){
        dto.id shouldBe 0
        dto.accessionNumber shouldBe product.accessionNumber?.value
    }

    @Test
    fun productEntityToDomainTest(){
        domain.accessionNumber shouldBe product.accessionNumber
    }
}