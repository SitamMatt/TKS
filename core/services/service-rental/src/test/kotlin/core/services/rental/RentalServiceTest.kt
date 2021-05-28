package core.services.rental

import core.domain.common.valueobjects.Email
import core.domain.rent.Client
import core.domain.rent.Product
import core.domain.rent.Rent
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import ports.rent.ClientSearchPort
import ports.rent.ProductSearchPort
import ports.rent.RentPersistencePort
import ports.rent.RentSearchPort
import java.util.*

@ExtendWith(MockKExtension::class)
internal class RentalServiceTest {
    private lateinit var rentService: RentalService
    private lateinit var sampleResource: Product
    private lateinit var sampleUser: Client
    private lateinit var sampleRent: Rent
    private val sampleEmail: Email = Email("mszewc@edu.pl")
    private val sampleEmail2: Email = Email("mzab@edu.pl")
    private val sampleResId: core.domain.common.valueobjects.AccessionNumber =
        core.domain.common.helpers.AccessionNumberHelper.generate()
    private val sampleRentId: UUID = UUID.randomUUID()

    @RelaxedMockK
    lateinit var resourceSearchPort: ProductSearchPort

    @RelaxedMockK
    lateinit var userSearchPort: ClientSearchPort

    @RelaxedMockK
    lateinit var rentSearchPort: RentSearchPort

    @RelaxedMockK
    lateinit var rentPersistencePort: RentPersistencePort

    @BeforeEach
    fun init() {
        sampleUser = Client(sampleEmail, true)
        sampleResource = Product(sampleResId)
        sampleRent = Rent(sampleRentId, Date(), null, sampleEmail2, sampleResId)
        rentService = RentalService(rentPersistencePort, rentSearchPort, userSearchPort, resourceSearchPort)
    }

    @Test
    fun `Given invalid user id then rent should fail`() {
        every { userSearchPort.findByEmail(sampleUser.email) } returns null
        Assertions.assertThrows(core.domain.common.exceptions.UserNotFoundException::class.java) {
            rentService.rent(
                sampleUser.email, sampleResource.accessionNumber!!
            )
        }
        verify(exactly = 0) { rentPersistencePort.save(any()) }
    }

    @Test
    fun `Given invalid resource id then rent should fail`() {
        every { userSearchPort.findByEmail(sampleEmail) } returns sampleUser
        every { resourceSearchPort.findByAccessionNumber(sampleResId) } returns null
        Assertions.assertThrows(core.domain.common.exceptions.ResourceNotFoundException::class.java) {
            rentService.rent(
                sampleUser.email, sampleResource.accessionNumber!!
            )
        }
        verify(exactly = 0) { rentPersistencePort.save(any()) }
    }

    @Test
    fun `Given inactive user email then rent should fail`() {
        sampleUser.active = false
        every { userSearchPort.findByEmail(sampleEmail) } returns sampleUser
        every { resourceSearchPort.findByAccessionNumber(sampleResId) } returns sampleResource
        Assertions.assertThrows(core.domain.common.exceptions.UserNotActiveException::class.java) {
            rentService.rent(
                sampleUser.email, sampleResource.accessionNumber!!
            )
        }
        verify(exactly = 0) { rentPersistencePort.save(any()) }
    }

    @Test
    fun `Given already rent resource id then rent should fail`() {
        every { userSearchPort.findByEmail(sampleEmail) } returns sampleUser
        every { resourceSearchPort.findByAccessionNumber(sampleResId) } returns sampleResource
        every { rentSearchPort.findActiveByResourceId(sampleResId) } returns sampleRent
        Assertions.assertThrows(core.domain.common.exceptions.ResourceAlreadyRentException::class.java) {
            rentService.rent(
                sampleUser.email, sampleResource.accessionNumber!!
            )
        }
        verify(exactly = 0) { rentPersistencePort.save(any()) }
    }

    @Test
    fun `Given invalid user id then returnResource should fail`() {
//        every { userSearchPort.findByEmail(sampleEmail) } returns null
//        Assertions.assertThrows(core.domain.common.exceptions.UserNotFoundException::class.java) {
//            rentService.returnResource(
//                sampleEmail,
//                sampleResId
//            )
//        }
    }

    @Test
    fun `Given invalid resource id then returnResource should fail`() {
        every { userSearchPort.findByEmail(sampleEmail) } returns sampleUser
        every { resourceSearchPort.findByAccessionNumber(sampleResId) } returns null
//        Assertions.assertThrows(core.domain.common.exceptions.ResourceNotFoundException::class.java) {
//            rentService.returnResource(
//                sampleEmail,
//                sampleResId
//            )
//        }
    }

    @Test
    fun `Given not rent resource id returnResource should fail`() {
        every { userSearchPort.findByEmail(sampleEmail) } returns sampleUser
        every { resourceSearchPort.findByAccessionNumber(sampleResId) } returns sampleResource
        every { rentSearchPort.findActiveByResourceId(sampleResId) } returns null
//        Assertions.assertThrows(core.domain.common.exceptions.ResourceNotRentException::class.java) {
//            rentService.returnResource(
//                sampleEmail,
//                sampleResId
//            )
//        }
    }

    @Test
    fun `Given resource rent by other user then returnResource should fail`() {
        every { userSearchPort.findByEmail((sampleEmail)) } returns sampleUser
        every { resourceSearchPort.findByAccessionNumber((sampleResId)) } returns sampleResource
        sampleRent = Rent(sampleRentId, Date(), null, sampleEmail2, sampleResId)
        every { rentSearchPort.findActiveByResourceId(sampleResId) } returns sampleRent
//        Assertions.assertThrows(core.domain.common.exceptions.InvalidUserException::class.java) {
//            rentService.returnResource(
//                sampleEmail,
//                sampleResId
//            )
//        }
    }

    @Test
    fun `Given valid id then getDetails should return rent`() {
        val rent = Rent(sampleRentId, Date(), null, sampleEmail, sampleResId)
        every { (rentSearchPort.getById(sampleRentId)) } returns rent
        val result = rentService.getDetails(sampleRentId)
        Assertions.assertEquals(result, rent)
    }

    @Test
    fun `Given invalid id then getDetails should fail`() {
        every { rentSearchPort.getById(sampleRentId) } returns null
        Assertions.assertThrows(core.domain.common.exceptions.RentNotFoundException::class.java) {
            rentService.getDetails(
                sampleRentId
            )
        }
    }
}