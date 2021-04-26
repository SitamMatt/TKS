package application.services

import application.helpers.AccessionNumberHelper
import domain.exceptions.IncompatibleResourceFormatException
import domain.exceptions.ResourceBlockedByRentException
import domain.exceptions.ResourceNotFoundException
import domain.exceptions.UnknownResourceException
import domain.model.Book
import domain.model.Magazine
import domain.model.context.rents.Rent
import domain.model.traits.Resource
import domain.model.values.AccessionNumber
import domain.model.values.Email
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import ports.secondary.RentSearchPort
import ports.secondary.ResourcePersistencePort
import ports.secondary.ResourceSearchPort
import java.util.*

@ExtendWith(MockKExtension::class)
class ResourcesServiceTest {

    data class InvalidResource(
        override var accessionNumber: AccessionNumber?,
        override var title: String
    ) : Resource

    private lateinit var resourcesService: ResourcesService
    private lateinit var sampleBook: Resource
    private lateinit var sampleMagazine: Resource
    private lateinit var invalidResource: Resource
    private val sampleAccessionNumber: AccessionNumber = AccessionNumberHelper.generate()

    @RelaxedMockK
    lateinit var resourcePersistencePort: ResourcePersistencePort

    @RelaxedMockK
    lateinit var resourceSearchPort: ResourceSearchPort

    @RelaxedMockK
    lateinit var rentSearchPort: RentSearchPort

    @BeforeEach
    fun init() {
        resourcesService = ResourcesService(resourcePersistencePort, resourceSearchPort, rentSearchPort)
        sampleBook = Book(null, "Diuna", "Frank Herbert")
        sampleMagazine = Magazine(null, "Świerszczyk", "Nowa era")
        invalidResource = InvalidResource(null, "invalid")
    }

    @Test
    fun `Given resource of valid type then create should success`() {
        resourcesService.create(sampleBook)
        verify(exactly = 1) { (resourcePersistencePort).save(sampleBook) }
        Assertions.assertNotNull(sampleBook.accessionNumber)
    }

    @Test
    fun `Given resource of invalid type then create should fail`() {
        Assertions.assertThrows(UnknownResourceException::class.java) {
            resourcesService.create(
                invalidResource
            )
        }
        verify(exactly = 0) { resourcePersistencePort.save(any()) }
    }

    @Test
    fun `Given resource with not null id then create should fail`() {
        sampleBook.accessionNumber = sampleAccessionNumber
        Assertions.assertThrows(UnknownResourceException::class.java) {
            resourcesService.create(sampleBook)
        }
        verify(exactly = 0) { resourcePersistencePort.save(any()) }
    }

    @Test
    fun `Given valid resource with not null id then update should success`() {
        sampleMagazine.accessionNumber = sampleAccessionNumber
        every { (resourceSearchPort.findByAccessionNumber(sampleAccessionNumber)) }.returns(sampleMagazine)
        resourcesService.update(sampleMagazine)
        verify(exactly = 1) { (resourcePersistencePort).save(sampleMagazine) }
    }

    @Test
    fun `Given non existing resource then update should fail`() {
        sampleMagazine.accessionNumber = sampleAccessionNumber
        every { resourceSearchPort.findByAccessionNumber(sampleMagazine.accessionNumber!!) } returns null
        Assertions.assertThrows(ResourceNotFoundException::class.java) {
            resourcesService.update(sampleMagazine)
        }
        verify(exactly = 0) { resourcePersistencePort.save(any()) }
    }

    @Test
    fun `Given different resource type then update should fail`() {
        sampleMagazine.accessionNumber = sampleAccessionNumber
        sampleBook.accessionNumber = sampleAccessionNumber
        every { resourceSearchPort.findByAccessionNumber(sampleAccessionNumber) } returns sampleMagazine
        Assertions.assertThrows(
            IncompatibleResourceFormatException::class.java
        ) { resourcesService.update(sampleBook) }
        verify(exactly = 0) { resourcePersistencePort.save(any()) }
    }

    @Test
    fun `Given valid resource rd then remove should success`() {
        sampleMagazine.accessionNumber = sampleAccessionNumber
        every { resourceSearchPort.findByAccessionNumber(sampleAccessionNumber) } returns sampleMagazine
        every { rentSearchPort.findActiveByResourceId(sampleAccessionNumber) } returns null
        resourcesService.remove(sampleAccessionNumber)
        verify(exactly = 1) { resourcePersistencePort.remove(sampleMagazine) }
    }

    @Test
    fun `Given invalid resource id then remove should fail`() {
        every { resourceSearchPort.findByAccessionNumber(sampleAccessionNumber) } returns null
        Assertions.assertThrows(ResourceNotFoundException::class.java) {
            resourcesService.remove(sampleAccessionNumber)
        }
        verify(exactly = 0) { resourcePersistencePort.remove(any()) }
    }

    @Test
    fun `Given rent resource id then remove should fail`() {
        sampleMagazine.accessionNumber = sampleAccessionNumber
        every { resourceSearchPort.findByAccessionNumber(sampleAccessionNumber) } returns sampleMagazine
        every { rentSearchPort.findActiveByResourceId(sampleAccessionNumber) } returns Rent(
            UUID.randomUUID(),
            Date(),
            null,
            Email("mszewc@edu.pl"),
            sampleAccessionNumber
        )
        Assertions.assertThrows(ResourceBlockedByRentException::class.java) {
            resourcesService.remove(sampleAccessionNumber)
        }
        verify(exactly = 0) { resourcePersistencePort.remove(any()) }
    }

    @Test
    fun `Given valid resource id then getDetails should success`() {
        sampleMagazine.accessionNumber = sampleAccessionNumber
        every { resourceSearchPort.findByAccessionNumber(sampleMagazine.accessionNumber!!) } returns sampleMagazine

        val result = resourcesService.getDetails(sampleMagazine.accessionNumber!!)

        verify(exactly = 1) { resourceSearchPort.findByAccessionNumber(sampleMagazine.accessionNumber!!) }
        Assertions.assertEquals(sampleMagazine, result)
    }

    @Test
    fun `Given invalid resource id then getDetails should fail`() {
        every { resourceSearchPort.findByAccessionNumber(sampleAccessionNumber) } returns null
        Assertions.assertThrows(ResourceNotFoundException::class.java) {
            resourcesService.getDetails(sampleAccessionNumber)
        }
    }
}