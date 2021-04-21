package webservice.adapters

import domain.model.Book
import domain.model.Rent
import domain.model.values.AccessionNumber
import domain.model.values.Email
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import ports.primary.combined.IRentService
import webservice.mappers.RentMapper
import webservice.mappers.ResourceMapper
import java.util.*

@ExtendWith(MockKExtension::class)
class RentWebServiceAdapterTest{

    private lateinit var adapter: RentWebServiceAdapter

    @RelaxedMockK
    private lateinit var rentService: IRentService

    private val mapper: RentMapper = RentMapper.INSTANCE

    @BeforeEach
    fun init(){
        adapter = RentWebServiceAdapter(rentService, mapper)
    }

    @Test
    fun `Given valid id, adapter should return appropriate Rent`(){
        val uuid = UUID.fromString("2768beab-db63-4a54-8cce-cc223d2463b5");
        every { rentService.getDetails(uuid) } returns Rent(
            uuid,
            Date(),
            null,
            Email("mszewc@edu.pl"),
            AccessionNumber("EEEE-254"),
        )
        val dto = adapter.getRent(uuid)
        assertEquals("EEEE-254",dto.resourceAccessionNumber)
        assertEquals("mszewc@edu.pl",dto.userEmail)
        assertEquals(uuid,dto.id)
    }

}