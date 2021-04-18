package application.services

import domain.exceptions.DuplicatedEmailException
import domain.exceptions.UserNotFoundException
import domain.model.User
import domain.model.UserRole
import domain.model.values.Email
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import ports.secondary.UserPersistencePort
import ports.secondary.UserSearchPort

@ExtendWith(MockitoExtension::class)
internal class UserServiceTest {
    lateinit var userService: UserService
    lateinit var sampleUser: User
    var sampleEmail: Email? = null

    @Mock
    lateinit var userPersistencePort: UserPersistencePort

    @Mock
    lateinit var userSearchPort: UserSearchPort

    @BeforeEach
    fun init() {
        userService = UserService(userPersistencePort, userSearchPort)
        sampleEmail = Email("mszewc@edu.pl")
        sampleUser = User(sampleEmail!!, UserRole.ADMIN, "####", true)
    }

    @Test
    @Throws(DuplicatedEmailException::class)
    fun GivenValidUser_RegistrationShouldSuccess() {
        Mockito.`when`(userSearchPort.findByEmail(ArgumentMatchers.eq(sampleEmail))).thenReturn(null)
        userService.register(sampleUser)
        Mockito.verify(userPersistencePort).add(ArgumentMatchers.eq(sampleUser))
    }

    @Test
    fun GivenUser_With_DuplicatedEmail_RegistrationShouldFail() {
        val duplicatedUser = User(sampleEmail!!, UserRole.CLIENT, "wwww", true)
        Mockito.`when`(userSearchPort.findByEmail(ArgumentMatchers.eq(sampleEmail))).thenReturn(duplicatedUser)
        Assertions.assertThrows(DuplicatedEmailException::class.java) { userService.register(sampleUser) }
        Mockito.verify(userPersistencePort, Mockito.never()).add(ArgumentMatchers.any())
    }

    @Test
    @Throws(UserNotFoundException::class)
    fun GivenValidEmailAndNewRole_ShouldSuccess() {
        val user = User(sampleEmail!!, UserRole.CLIENT, "wwww", true)
        Mockito.`when`(userSearchPort.findByEmail(ArgumentMatchers.eq(sampleEmail))).thenReturn(user)
        userService.changeRole(sampleEmail, UserRole.ADMIN)
        Mockito.verify(userPersistencePort).update(ArgumentMatchers.eq(user))
        Assertions.assertEquals(UserRole.ADMIN, user.role)
    }

    @Test
    @Throws(UserNotFoundException::class)
    fun GivenValidEmailAndSameRole_ShouldSuccess_ButNotPersist() {
        val user = User(sampleEmail!!, UserRole.CLIENT, "wwww", true)
        Mockito.`when`(userSearchPort.findByEmail(ArgumentMatchers.eq(sampleEmail))).thenReturn(user)
        userService.changeRole(sampleEmail, UserRole.CLIENT)
        Mockito.verify(userPersistencePort, Mockito.never()).update(ArgumentMatchers.any())
        Assertions.assertEquals(UserRole.CLIENT, user.role)
    }

    @Test
    fun GivenInvalidEmailAndAnyRole_ShouldFail() {
        Mockito.`when`(userSearchPort.findByEmail(ArgumentMatchers.eq(sampleEmail))).thenReturn(null)
        Assertions.assertThrows(UserNotFoundException::class.java) {
            userService.changeRole(
                sampleEmail,
                UserRole.CLIENT
            )
        }
        Mockito.verify(userPersistencePort, Mockito.never()).update(ArgumentMatchers.any())
    }

    @Test
    fun GivenInvalidEmailAndAnyState_ShouldFail() {
        Mockito.`when`(userSearchPort.findByEmail(ArgumentMatchers.eq(sampleEmail))).thenReturn(null)
        Assertions.assertThrows(UserNotFoundException::class.java) { userService.changeState(sampleEmail, true) }
        Mockito.verify(userPersistencePort, Mockito.never()).update(ArgumentMatchers.any())
    }

    @Test
    @Throws(UserNotFoundException::class)
    fun GivenValidEmailAndSameState_ShouldSuccess_ButNotPersist() {
        val user = User(sampleEmail!!, UserRole.CLIENT, "wwww", true)
        Mockito.`when`(userSearchPort.findByEmail(ArgumentMatchers.eq(sampleEmail))).thenReturn(user)
        userService.changeState(sampleEmail, true)
        Mockito.verify(userPersistencePort, Mockito.never()).update(ArgumentMatchers.any())
        Assertions.assertTrue(user.active)
    }

    @Test
    @Throws(UserNotFoundException::class)
    fun GivenValidEmailAndNewState_ShouldSuccess() {
        val user = User(sampleEmail!!, UserRole.CLIENT, "wwww", true)
        Mockito.`when`(userSearchPort.findByEmail(ArgumentMatchers.eq(sampleEmail))).thenReturn(user)
        userService.changeState(sampleEmail, false)
        Mockito.verify(userPersistencePort).update(ArgumentMatchers.eq(user))
        Assertions.assertFalse(user.active)
    }

    @Test
    @Throws(UserNotFoundException::class)
    fun GivenValidEmail_ShouldReturnUserDetails() {
        val user = User(sampleEmail!!, UserRole.CLIENT, "wwww", true)
        Mockito.`when`(userSearchPort.findByEmail(ArgumentMatchers.eq(sampleEmail))).thenReturn(user)
        val result = userService.getDetails(sampleEmail!!)
        Assertions.assertSame(result, user)
        Assertions.assertEquals(result, user)
    }

    @Test
    fun GivenInvalidEmail_ShouldFail() {
        Mockito.`when`(userSearchPort.findByEmail(ArgumentMatchers.eq(sampleEmail))).thenReturn(null)
        Assertions.assertThrows(UserNotFoundException::class.java) { userService.getDetails(sampleEmail!!) }
    }
}