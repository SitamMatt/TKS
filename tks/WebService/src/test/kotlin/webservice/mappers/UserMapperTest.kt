package webservice.mappers

import domain.model.context.users.User
import domain.model.UserRole
import domain.model.values.Email
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UserMapperTest{


    private lateinit var mapper: UserMapper

    @BeforeEach
    fun init(){
        mapper = UserMapper.INSTANCE
    }

    @Test
    fun `to DTO`(){
        val user = User(Email("mszewc@edu.pl"), UserRole.ADMIN, "password", true)
        val dto = mapper.toDto(user)
        assertNotNull(dto!!)
        assertEquals("mszewc@edu.pl", dto.email)
        assertEquals(true, dto.active)
        assertEquals("ADMIN", dto.role)
    }

    @Test
    fun `nullable to DTO`(){
        val dto = mapper.toDto(null)
        assertNull(dto)
    }
}