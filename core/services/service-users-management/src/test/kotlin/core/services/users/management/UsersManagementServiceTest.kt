package core.services.users.management

import core.domain.common.UserRole
import core.domain.common.exceptions.DuplicatedEmailException
import core.domain.common.valueobjects.Email
import core.domain.user.User
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import ports.user.UserPersistencePort
import ports.user.UserSearchPort

@ExtendWith(MockKExtension::class)
class UsersManagementServiceTest {
    private lateinit var userService: UsersManagementService
    private lateinit var sampleUser: User
    private val sampleEmail: Email = Email("mszewc@edu.pl")

    @RelaxedMockK
    lateinit var userPersistencePort: UserPersistencePort

    @RelaxedMockK
    lateinit var userSearchPort: UserSearchPort

    @BeforeEach
    fun init() {
        userService = UsersManagementService(userPersistencePort, userSearchPort)
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
        userService.changeRole(sampleEmail, core.domain.common.UserRole.CLIENT)
        verify(exactly = 0) { userPersistencePort.save(any()) }
        Assertions.assertEquals(core.domain.common.UserRole.CLIENT, user.role)
    }

    @Test
    fun `Given invalid email and any role then changeRole should fail`() {
        every { userSearchPort.findByEmail(sampleEmail) } returns null
        assertThrows(core.domain.common.exceptions.UserNotFoundException::class.java) {
            userService.changeRole(
                sampleEmail,
                core.domain.common.UserRole.CLIENT
            )
        }
        verify(exactly = 0) { userPersistencePort.save(any()) }
    }

    @Test
    fun `Given invalid email and any state then changeState should fail`() {
        every { userSearchPort.findByEmail(sampleEmail) } returns null
        assertThrows(core.domain.common.exceptions.UserNotFoundException::class.java) { userService.changeState(sampleEmail, true) }
        verify(exactly = 0) { userPersistencePort.save(any()) }
    }

    @Test
    @Throws(core.domain.common.exceptions.UserNotFoundException::class)
    fun `Given valid email and same state then changeState should success but not persist`() {
        val user = User(sampleEmail, UserRole.CLIENT, "password", true)
        every { userSearchPort.findByEmail(sampleEmail) } returns user
        userService.changeState(sampleEmail, true)
        verify(exactly = 0) { userPersistencePort.save(any()) }
        Assertions.assertTrue(user.active)
    }

    @Test
    @Throws(core.domain.common.exceptions.UserNotFoundException::class)
    fun `Given valid email and new state then changeState should success`() {
        val user = User(sampleEmail, UserRole.CLIENT, "password", true)
        every { (userSearchPort.findByEmail(sampleEmail)) } returns (user)
        userService.changeState(sampleEmail, false)
        verify(exactly = 1) { (userPersistencePort).save(user) }
        Assertions.assertFalse(user.active)
    }

    @Test
    @Throws(core.domain.common.exceptions.UserNotFoundException::class)
    fun `Given valid email then getDetails should return user details`() {
        val user = User(sampleEmail, UserRole.CLIENT, "password", true)
        every { (userSearchPort.findByEmail(sampleEmail)) } returns (user)
        val result = userService.getDetails(sampleEmail)
        Assertions.assertEquals(result, user)
    }

    @Test
    fun `Given invalid email then getDetails should return null`() {
        every { userSearchPort.findByEmail(sampleEmail) } returns null
        val result = userService.getDetails(sampleEmail)
        Assertions.assertNull(result)
    }
}