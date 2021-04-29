package repository.adapters

import domain.model.context.users.User
import domain.model.UserRole
import domain.model.values.Email
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import repository.data.UserEntity
import repository.mappers.UserMapper
import repository.repositories.IRepository
import java.util.*

@ExtendWith(MockKExtension::class)
class UserRepositoryAdapterTest {

    private lateinit var adapter: UserRepositoryAdapter

    private var mapper: UserMapper = UserMapper.INSTANCE

    @RelaxedMockK
    lateinit var repository: IRepository<UserEntity>

    @BeforeEach
    fun init() {
        adapter = UserRepositoryAdapter(repository, mapper)
    }

    @Test
    fun `Given valid email, adapter should return a user assigned to that email`() {
        every { repository.find(any()) } returns UserEntity(
            UUID.randomUUID(),
            "mszewc@edu.pl",
            "ADMIN",
            "password",
            true
        )
        val user = adapter.findByEmail(Email("mszewc@edu.pl"))
        assertNotNull(user!!)
        verify(exactly = 1) { repository.find(any()) }
        assertEquals("mszewc@edu.pl", user.email.value)
    }

    @Test
    fun `Given invalid email, adapter should return null`() {
        every { repository.find(any()) } returns null
        val user = adapter.findByEmail(Email("mszewc@edu.pl"))
        assertNull(user)
        verify(exactly = 1) { repository.find(any()) }
    }

    @Test
    fun `Given valid new User, adapter should persist new User in repository`() {
        val user = User(Email("mszewc@edu.pl"), UserRole.ADMIN, "password", true)
        every { repository.find(any()) } returns null
        adapter.save(user)
        verify(exactly = 1) { repository.add(any()) }
        verify(exactly = 0) { repository.update(any()) }
    }

    @Test
    fun `Given valid existing User, adapter should update User in repository`() {
        val user = User(Email("mszewc@edu.pl"), UserRole.ADMIN, "password", true)
        val entity = UserEntity(UUID.randomUUID(), "mszewc@edu.pl", "ADMIN", "password", false)
        every { repository.find(any())} returns entity
        adapter.save(user)
        verify(exactly = 1) { repository.update(any()) }
        verify(exactly = 0) { repository.add(any()) }
        assertEquals(true, entity.active)
    }

}