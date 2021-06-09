package repositories.rental.mappers

import core.domain.common.valueobjects.Email
import core.domain.rent.Client
import org.junit.jupiter.api.Test
import io.kotest.matchers.shouldBe

class ClientMapperTest {
    private val client = Client(Email("matz@edu.pl"), true)

    @Test
    fun clientToEntityTest(){
        val dto = client.toEntity()

        dto.email shouldBe client.email.value
        dto.active shouldBe client.active
        dto.id shouldBe 0
    }

    @Test
    fun clientEntityToDomainTest(){
        val dto = client.toEntity()
        val domain = dto.toDomain()

        domain.email.value shouldBe client.email.value
        domain.active shouldBe client.active
    }
}