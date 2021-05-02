package core.services.resources.management

import domain.common.exceptions.IncompatibleResourceFormatException
import domain.common.exceptions.ResourceBlockedByRentException
import domain.common.exceptions.ResourceNotFoundException
import domain.common.exceptions.UnknownResourceException
import domain.common.helpers.AccessionNumberHelper
import domain.common.valueobjects.AccessionNumber
import domain.resource.Book
import domain.resource.Magazine
import domain.resource.Resource
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import ports.resource.ResourcePersistencePort
import ports.resource.ResourceSearchPort

@ExtendWith(MockKExtension::class)
class ResourcesManagementServiceTest {

    data class InvalidResource(
        override var accessionNumber: AccessionNumber?,
        override var title: String,
        override var locked: Boolean
    ) : Resource

    private lateinit var resourcesService: ResourcesManagementService
    private lateinit var sampleBook: Resource
    private lateinit var sampleMagazine: Resource
    private lateinit var invalidResource: Resource
    private val sampleAccessionNumber: AccessionNumber = AccessionNumberHelper.generate()

    @RelaxedMockK
    lateinit var resourcePersistencePort: ResourcePersistencePort

    @RelaxedMockK
    lateinit var resourceSearchPort: ResourceSearchPort

    @BeforeEach
    fun init() {
        resourcesService = ResourcesManagementService(resourcePersistencePort, resourceSearchPort)
        sampleBook = Book(null, "Diuna", false, "Frank Herbert")
        sampleMagazine = Magazine(null, "Świerszczyk", false, "Nowa era")
        invalidResource = InvalidResource(null, "invalid", false)
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
    fun `Given locked resource id then remove should fail`() {
        sampleMagazine.accessionNumber = sampleAccessionNumber
        sampleMagazine.locked = true
        every { resourceSearchPort.findByAccessionNumber(sampleAccessionNumber) } returns sampleMagazine
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