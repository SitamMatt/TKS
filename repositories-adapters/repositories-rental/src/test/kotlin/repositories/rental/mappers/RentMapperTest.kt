package repositories.rental.mappers

import core.domain.common.valueobjects.AccessionNumber
import core.domain.common.valueobjects.Email
import core.domain.rent.Client
import core.domain.rent.Product
import core.domain.rent.Rent
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.util.*

class RentMapperTest {
    private val client = Client(Email("matz@edu.pl"), true)
    private val product = Product(AccessionNumber("EEEE-121"))
    private val rent = Rent(UUID.randomUUID(), Date(), null, Email("matz@edu.pl"), AccessionNumber("EEEE-121"))
    private val dto = rent.toEntity(client.toEntity(), product.toEntity())
    private val domain = dto.toDomain()

    @Test
    fun rentToEntityTest(){
        dto.client shouldBe client.toEntity()
        dto.product shouldBe product.toEntity()
        dto.client.email shouldBe client.email.value
        dto.startDate shouldBe rent.startDate
        dto.identifier shouldBe rent.id
        dto.id shouldBe 0
    }

    @Test
    fun rentEntityToDomainTest(){
        domain.startDate shouldBe rent.startDate
        domain.endDate shouldBe rent.endDate
        domain.resourceId shouldBe rent.resourceId
        domain.userEmail shouldBe rent.userEmail
    }
}