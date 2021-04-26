package webservice.adapters

import domain.model.context.library.Book
import domain.model.values.AccessionNumber
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import ports.primary.combined.IResourceService
import webservice.mappers.ResourceMapper

@ExtendWith(MockKExtension::class)
class LibraryItemWebServiceAdapterTest{

    private lateinit var adapter: LibraryItemWebServiceAdapter

    @RelaxedMockK
    private lateinit var resourceService: IResourceService

    private val mapper: ResourceMapper = ResourceMapper.INSTANCE

    @BeforeEach
    fun init(){
        adapter = LibraryItemWebServiceAdapter(resourceService, mapper)
    }

    @Test
    fun `Given valid accessionNumber, adapter should return appropriate LibraryItem`(){
        every { resourceService.getDetails(AccessionNumber("EEEE-254")) } returns Book(
            AccessionNumber("EEEE-254"),
            "Diuna",
            "Frank Herbert"
        )
        val dto = adapter.getLibraryItem("EEEE-254")
        assertEquals("EEEE-254",dto.accessionNumber)
        assertEquals("Diuna",dto.title)
        assertEquals("Frank Herbert",dto.author)
        assertEquals("BOOK",dto.type)
        assertNull(dto.publisher)
    }
}