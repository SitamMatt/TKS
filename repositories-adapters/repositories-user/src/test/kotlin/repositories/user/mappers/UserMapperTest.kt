package repositories.user.mappers

import core.domain.common.UserRole
import core.domain.common.valueobjects.Email
import core.domain.user.User
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class UserMapperTest {
    private val user = User(Email("matz@edu.pl"), UserRole.CLIENT, "####", true)
    private val dto = user.toEntity()
    private val domain = dto.toDomain()

    @Test
    fun userToEntity(){
        dto.email shouldBe user.email.value
        dto.id shouldBe 0
        dto.active shouldBe user.active
        dto.role shouldBe user.role.name
    }

    @Test
    fun userEntityToDomain(){
        domain.email.value shouldBe user.email.value
        domain.active shouldBe user.active
        domain.role.name shouldBe user.role.name
    }
}