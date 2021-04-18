package application.services

import application.helpers.AccessionNumberHelper
import domain.exceptions.*
import domain.model.Book
import domain.model.Rent
import domain.model.User
import domain.model.UserRole
import domain.model.traits.Resource
import domain.model.values.AccessionNumber
import domain.model.values.Email
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import ports.secondary.RentPersistencePort
import ports.secondary.RentSearchPort
import ports.secondary.ResourceSearchPort
import ports.secondary.UserSearchPort
import java.util.*

@ExtendWith(MockitoExtension::class)
internal class RentServiceTest {
    lateinit var rentService: RentService
    lateinit var sampleResource: Resource
    lateinit var sampleUser: User
    lateinit var sampleRent: Rent
    var sampleEmail: Email? = null
    var sampleEmail2: Email? = null
    var sampleResId: AccessionNumber? = null
    lateinit var sampleRentId: UUID

    @Mock
    lateinit var resourceSearchPort: ResourceSearchPort

    @Mock
    lateinit var userSearchPort: UserSearchPort

    @Mock
    lateinit var rentSearchPort: RentSearchPort

    @Mock
    lateinit var rentPersistencePort: RentPersistencePort

    @BeforeEach
    fun init() {
        sampleEmail = Email("mszewc@edu.pl")
        sampleEmail2 = Email("mzab@edu.pl")
        sampleResId = AccessionNumberHelper.generate()
        sampleRentId = UUID.randomUUID()
        sampleUser = User(sampleEmail!!, UserRole.CLIENT, "####", true)
        sampleResource = Book(sampleResId, "Diuna", "Frank Herbert")
        sampleRent = Rent(sampleRentId, Date(), null, sampleEmail2!!, sampleResId!!)
        rentService = RentService(rentPersistencePort, rentSearchPort, userSearchPort, resourceSearchPort)
    }

    @Test
    fun GivenInvalidUserId_Rent_ShouldFail() {
        Mockito.`when`(userSearchPort.findByEmail(ArgumentMatchers.eq(sampleUser.email))).thenReturn(null)
        Assertions.assertThrows(UserNotFoundException::class.java) {
            rentService.rent(
                sampleUser.email, sampleResource.accessionNumber!!
            )
        }
        Mockito.verify(rentPersistencePort, Mockito.never())?.save(ArgumentMatchers.any())
    }

    @Test
    fun GivenInvalidResourceId_Rent_ShouldFail() {
        Mockito.`when`(userSearchPort.findByEmail(ArgumentMatchers.eq(sampleEmail))).thenReturn(sampleUser)
        Mockito.`when`(resourceSearchPort.findById(ArgumentMatchers.eq(sampleResId))).thenReturn(null)
        Assertions.assertThrows(ResourceNotFoundException::class.java) {
            rentService.rent(
                sampleUser.email, sampleResource.accessionNumber!!
            )
        }
        Mockito.verify(rentPersistencePort, Mockito.never()).save(ArgumentMatchers.any())
    }

    @Test
    fun GivenNonActiveUserId_Rent_ShouldFail() {
        sampleUser.active = false
        Mockito.`when`(userSearchPort.findByEmail(ArgumentMatchers.eq(sampleEmail))).thenReturn(sampleUser)
        Mockito.`when`(resourceSearchPort.findById(ArgumentMatchers.eq(sampleResId))).thenReturn(sampleResource)
        Assertions.assertThrows(UserNotActiveException::class.java) {
            rentService.rent(
                sampleUser.email, sampleResource.accessionNumber!!
            )
        }
        Mockito.verify(rentPersistencePort, Mockito.never()).save(ArgumentMatchers.any())
    }

    @Test
    fun GivenAlreadyRentResId_Rent_ShouldFail() {
        Mockito.`when`(userSearchPort.findByEmail(ArgumentMatchers.eq(sampleEmail))).thenReturn(sampleUser)
        Mockito.`when`(resourceSearchPort.findById(ArgumentMatchers.eq(sampleResId))).thenReturn(sampleResource)
        Mockito.`when`(rentSearchPort.findActiveByResourceId(sampleResId)).thenReturn(sampleRent)
        Assertions.assertThrows(ResourceAlreadyRentException::class.java) {
            rentService.rent(
                sampleUser.email, sampleResource.accessionNumber!!
            )
        }
        Mockito.verify(rentPersistencePort, Mockito.never()).save(ArgumentMatchers.any())
    }

    @Test
    fun GivenInvalidUserId_Return_ShouldFail() {
        Mockito.`when`(userSearchPort.findByEmail(ArgumentMatchers.eq(sampleEmail))).thenReturn(null)
        Assertions.assertThrows(UserNotFoundException::class.java) {
            rentService.returnResource(
                sampleEmail!!,
                sampleResId!!
            )
        }
    }

    @Test
    fun GivenInvalidResourceId_Return_ShouldFail() {
        Mockito.`when`(userSearchPort.findByEmail(ArgumentMatchers.eq(sampleEmail))).thenReturn(sampleUser)
        Mockito.`when`(resourceSearchPort.findById(ArgumentMatchers.eq(sampleResId))).thenReturn(null)
        Assertions.assertThrows(ResourceNotFoundException::class.java) {
            rentService.returnResource(
                sampleEmail!!,
                sampleResId!!
            )
        }
    }

    @Test
    fun GivenNotRentResourceId_Return_ShouldFail() {
        Mockito.`when`(userSearchPort.findByEmail(ArgumentMatchers.eq(sampleEmail))).thenReturn(sampleUser)
        Mockito.`when`(resourceSearchPort.findById(ArgumentMatchers.eq(sampleResId))).thenReturn(sampleResource)
        Mockito.`when`(rentSearchPort.findActiveByResourceId(sampleResId)).thenReturn(null)
        Assertions.assertThrows(ResourceNotRentException::class.java) {
            rentService.returnResource(
                sampleEmail!!,
                sampleResId!!
            )
        }
    }

    @Test
    fun GivenResourceRentByOtherUser_Return_ShouldFail() {
        Mockito.`when`(userSearchPort.findByEmail(ArgumentMatchers.eq(sampleEmail))).thenReturn(sampleUser)
        Mockito.`when`(resourceSearchPort.findById(ArgumentMatchers.eq(sampleResId))).thenReturn(sampleResource)
        sampleRent = Rent(sampleRentId, Date(), null, sampleEmail2!!, sampleResId!!)
        Mockito.`when`(rentSearchPort.findActiveByResourceId(sampleResId)).thenReturn(sampleRent)
        Assertions.assertThrows(InvalidUserException::class.java) {
            rentService.returnResource(
                sampleEmail!!,
                sampleResId!!
            )
        }
    }
}