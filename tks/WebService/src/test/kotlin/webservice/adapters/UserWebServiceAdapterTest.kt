package webservice.adapters

import domain.model.context.users.User
import domain.model.UserRole
import domain.model.values.Email
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import ports.primary.combined.IUserService
import webservice.mappers.UserMapper

@ExtendWith(MockKExtension::class)
class UserWebServiceAdapterTest{

    private lateinit var adapter: UserWebServiceAdapter

    @RelaxedMockK
    private lateinit var userService: IUserService

    private val mapper: UserMapper = UserMapper.INSTANCE

    @BeforeEach
    fun init(){
        adapter = UserWebServiceAdapter(userService, mapper)
    }

    @Test
    fun `Given valid email, adapter should return appropriate User`(){
        every { userService.getDetails(Email("mszewc@edu.pl")) } returns User(
            Email("mszewc@edu.pl"),
            UserRole.ADMIN,
            "password",
            true
        )
        val dto = adapter.getUser("mszewc@edu.pl")
        assertEquals("mszewc@edu.pl", dto.email)
        assertEquals("ADMIN", dto.role)
        assertEquals(true, dto.active)
    }
}