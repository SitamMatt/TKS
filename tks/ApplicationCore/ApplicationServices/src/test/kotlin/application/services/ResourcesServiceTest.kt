package application.services

import application.helpers.AccessionNumberHelper
import domain.exceptions.IncompatibleResourceFormatException
import domain.exceptions.ResourceBlockedByRentException
import domain.exceptions.ResourceNotFoundException
import domain.exceptions.UnknownResourceException
import domain.model.Book
import domain.model.Magazine
import domain.model.Rent
import domain.model.traits.Resource
import domain.model.values.AccessionNumber
import domain.model.values.Email
import lombok.SneakyThrows
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import ports.secondary.RentSearchPort
import ports.secondary.ResourcePersistencePort
import ports.secondary.ResourceSearchPort
import java.util.*

@ExtendWith(MockitoExtension::class)
class ResourcesServiceTest {

    data class InvalidResource(
        override var accessionNumber: AccessionNumber?,
        override var title: String
    ) : Resource

    lateinit var resourcesService: ResourcesService
    lateinit var sampleBook: Resource
    lateinit var sampleMagazine: Resource
    lateinit var invalidResource: Resource
    var sampleAccessionNumber: AccessionNumber? = null

    @Mock
    lateinit var resourcePersistencePort: ResourcePersistencePort

    @Mock
    lateinit var resourceSearchPort: ResourceSearchPort

    @Mock
    lateinit var rentSearchPort: RentSearchPort

    @BeforeEach
    fun init() {
        sampleAccessionNumber = AccessionNumberHelper.generate()
        resourcesService = ResourcesService(resourcePersistencePort, resourceSearchPort, rentSearchPort)
        sampleBook = Book(null, "Diuna", "Frank Herbert")
        sampleMagazine = Magazine(null, "Świerszczyk", "Nowa era")
        invalidResource = InvalidResource(null, "invalid")
    }

    @Test
    @Throws(UnknownResourceException::class)
    fun GivenResourceOfValidType_Create_ShouldSuccess() {
        resourcesService.create(sampleBook)
        Mockito.verify(resourcePersistencePort).add(ArgumentMatchers.eq(sampleBook))
        Assertions.assertNotNull(sampleBook.accessionNumber)
    }

    @Test
    fun GivenResourceOfInvalidType_Create_ShouldFail() {
        Assertions.assertThrows(UnknownResourceException::class.java) {
            resourcesService.create(
                invalidResource
            )
        }
        Mockito.verify(resourcePersistencePort, Mockito.never()).add(ArgumentMatchers.any())
    }

    @Test
    fun GivenResourceWithNotNullId_Create_ShouldFail() {
        sampleBook.accessionNumber = sampleAccessionNumber
        Assertions.assertThrows(UnknownResourceException::class.java) {
            resourcesService.create(sampleBook)
        }
        Mockito.verify(resourcePersistencePort, Mockito.never()).add(ArgumentMatchers.any())
    }

    @Test
    fun GivenValidResourceWithNotNullId_Update_ShouldSuccess() {
        sampleMagazine.accessionNumber = sampleAccessionNumber
        Mockito.`when`(resourceSearchPort.findById(ArgumentMatchers.eq(sampleAccessionNumber)))
            .thenReturn(sampleMagazine)
        resourcesService.update(sampleMagazine)
        Mockito.verify(resourcePersistencePort).save(sampleMagazine)
    }

    @Test
    fun GivenNonExistingResource_Update_ShouldFail() {
        sampleMagazine.accessionNumber = sampleAccessionNumber
        Mockito.`when`(
            resourceSearchPort.findById(
                ArgumentMatchers.eq(sampleMagazine.accessionNumber)
            )
        ).thenReturn(null)
        Assertions.assertThrows(ResourceNotFoundException::class.java) {
            resourcesService.update(
                sampleMagazine
            )
        }
        Mockito.verify(resourcePersistencePort, Mockito.never()).save(ArgumentMatchers.any())
    }

    @Test
    fun GivenOtherResourceType_Update_ShouldFail() {
        sampleMagazine.accessionNumber = sampleAccessionNumber
        sampleBook.accessionNumber = sampleAccessionNumber
        Mockito.`when`(resourceSearchPort.findById(ArgumentMatchers.eq(sampleAccessionNumber)))
            .thenReturn(sampleMagazine)
        Assertions.assertThrows(
            IncompatibleResourceFormatException::class.java
        ) {
            resourcesService.update(sampleBook)
        }
        Mockito.verify(resourcePersistencePort, Mockito.never()).save(ArgumentMatchers.any())
    }

    @Test
    fun GivenValidResourceId_Remove_ShouldSuccess() {
        sampleMagazine.accessionNumber = sampleAccessionNumber
        Mockito.`when`(resourceSearchPort.findById(ArgumentMatchers.eq(sampleAccessionNumber)))
            .thenReturn(sampleMagazine)
        Mockito.`when`(rentSearchPort.findActiveByResourceId(ArgumentMatchers.eq(sampleAccessionNumber)))
            .thenReturn(null)
        resourcesService.remove(sampleAccessionNumber!!)
        Mockito.verify(resourcePersistencePort).remove(sampleMagazine)
    }

    @Test
    fun GivenInvalidResourceId_Remove_ShouldFail() {
        Mockito.`when`(resourceSearchPort.findById(ArgumentMatchers.eq(sampleAccessionNumber))).thenReturn(null)
        Assertions.assertThrows(ResourceNotFoundException::class.java) {
            resourcesService.remove(sampleAccessionNumber!!)
        }
        Mockito.verify(resourcePersistencePort, Mockito.never()).remove(ArgumentMatchers.any())
    }

    @SneakyThrows
    @Test
    fun GivenRentResourceId_Remove_ShouldFail() {
        sampleMagazine.accessionNumber = sampleAccessionNumber
        Mockito.`when`(resourceSearchPort.findById(ArgumentMatchers.eq(sampleAccessionNumber)))
            .thenReturn(sampleMagazine)
        Mockito.`when`(rentSearchPort.findActiveByResourceId(ArgumentMatchers.eq(sampleAccessionNumber)))
            .thenReturn(Rent(UUID.randomUUID(), Date(), null, Email("mszewc@edu.pl"), sampleAccessionNumber!!))
        Assertions.assertThrows(ResourceBlockedByRentException::class.java) {
            resourcesService.remove(sampleAccessionNumber!!)
        }
        Mockito.verify(resourcePersistencePort, Mockito.never()).remove(ArgumentMatchers.any())
    }

    @Test
    @Throws(ResourceNotFoundException::class)
    fun GivenValidResourceId_GetDetails_ShouldSuccess() {
        sampleMagazine.accessionNumber = sampleAccessionNumber
        Mockito.`when`(
            resourceSearchPort.findById(
                ArgumentMatchers.eq(sampleMagazine.accessionNumber)
            )
        ).thenReturn(sampleMagazine)
        val result = resourcesService.getDetails(sampleMagazine.accessionNumber!!)
        Mockito.verify(resourceSearchPort).findById(
            ArgumentMatchers.eq(
                sampleMagazine.accessionNumber
            )
        )
        Assertions.assertEquals(sampleMagazine, result)
    }

    @Test
    fun GivenInvalidResourceId_GetDetails_ShouldFail() {
        Mockito.`when`(resourceSearchPort.findById(ArgumentMatchers.eq(sampleAccessionNumber))).thenReturn(null)
        Assertions.assertThrows(ResourceNotFoundException::class.java) {
            resourcesService.getDetails(
                sampleAccessionNumber!!
            )
        }
    }
}