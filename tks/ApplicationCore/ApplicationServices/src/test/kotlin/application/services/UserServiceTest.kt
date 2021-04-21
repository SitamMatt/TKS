package application.services

import domain.exceptions.DuplicatedEmailException
import domain.exceptions.UserNotFoundException
import domain.model.User
import domain.model.UserRole
import domain.model.values.Email
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import ports.secondary.UserPersistencePort
import ports.secondary.UserSearchPort


@ExtendWith(MockKExtension::class)
class UserServiceTest {
    private lateinit var userService: UserService
    private lateinit var sampleUser: User
    private val sampleEmail: Email = Email("mszewc@edu.pl")

    @RelaxedMockK
    lateinit var userPersistencePort: UserPersistencePort

    @RelaxedMockK
    lateinit var userSearchPort: UserSearchPort

    @BeforeEach
    fun init() {
        userService = UserService(userPersistencePort, userSearchPort)
        sampleUser = User(sampleEmail, UserRole.ADMIN, "####", true)
    }

    @Test
    fun `Given valid user then registration should success`() {
        every { userSearchPort.findByEmail(sampleEmail) } returns null
        userService.register(sampleUser)
        verify(exactly = 1) { userPersistencePort.save(sampleUser) }
    }

    @Test
    fun `Given user with duplicated email then registration should fail`() {
        val duplicatedUser = User(sampleEmail, UserRole.CLIENT, "password", true)
        every { userSearchPort.findByEmail(sampleEmail) } returns duplicatedUser
        assertThrows(DuplicatedEmailException::class.java) { userService.register(sampleUser) }
        verify(exactly = 0) { userPersistencePort.save(any()) }
    }

    @Test
    fun `Given valid email and new role then changeRole should success`() {
        val user = User(sampleEmail, UserRole.CLIENT, "password", true)
        every { userSearchPort.findByEmail(sampleEmail) } returns user
        userService.changeRole(sampleEmail, UserRole.ADMIN)
        verify(exactly = 1) { userPersistencePort.save(user) }
        Assertions.assertEquals(UserRole.ADMIN, user.role)
    }

    @Test
    fun `Given valid email and same role then changeRole should success but not persist`() {
        val user = User(sampleEmail, UserRole.CLIENT, "password", true)
        every { userSearchPort.findByEmail(sampleEmail) } returns user
        userService.changeRole(sampleEmail, UserRole.CLIENT)
        verify(exactly = 0) { userPersistencePort.save(any()) }
        Assertions.assertEquals(UserRole.CLIENT, user.role)
    }

    @Test
    fun `Given invalid email and any role then changeRole should fail`() {
        every { userSearchPort.findByEmail(sampleEmail) } returns null
        assertThrows(UserNotFoundException::class.java) {
            userService.changeRole(
                sampleEmail,
                UserRole.CLIENT
            )
        }
        verify(exactly = 0) { userPersistencePort.save(any()) }
    }

    @Test
    fun `Given invalid email and any state then changeState should fail`() {
        every { userSearchPort.findByEmail(sampleEmail) } returns null
        assertThrows(UserNotFoundException::class.java) { userService.changeState(sampleEmail, true) }
        verify(exactly = 0) { userPersistencePort.save(any()) }
    }

    @Test
    @Throws(UserNotFoundException::class)
    fun `Given valid email and same state then changeState should success but not persist`() {
        val user = User(sampleEmail, UserRole.CLIENT, "password", true)
        every { userSearchPort.findByEmail(sampleEmail) } returns user
        userService.changeState(sampleEmail, true)
        verify(exactly = 0) { userPersistencePort.save(any()) }
        Assertions.assertTrue(user.active)
    }

    @Test
    @Throws(UserNotFoundException::class)
    fun `Given valid email and new state then changeState should success`() {
        val user = User(sampleEmail, UserRole.CLIENT, "password", true)
        every { (userSearchPort.findByEmail(sampleEmail)) } returns (user)
        userService.changeState(sampleEmail, false)
        verify(exactly = 1) { (userPersistencePort).save(user) }
        Assertions.assertFalse(user.active)
    }

    @Test
    @Throws(UserNotFoundException::class)
    fun `Given valid email then getDetails should return user details`() {
        val user = User(sampleEmail, UserRole.CLIENT, "password", true)
        every { (userSearchPort.findByEmail(sampleEmail)) }returns (user)
        val result = userService.getDetails(sampleEmail)
        Assertions.assertEquals(result, user)
    }

    @Test
    fun `Given invalid email then getDetails should fail`() {
        every { userSearchPort.findByEmail(sampleEmail) }returns null
        assertThrows(UserNotFoundException::class.java) { userService.getDetails(sampleEmail) }
    }
}