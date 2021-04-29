package repository.adapters

import domain.model.context.rents.Rent
import domain.model.values.AccessionNumber
import domain.model.values.Email
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import repository.data.*
import repository.mappers.RentMapper
import repository.repositories.IRepository
import java.util.*

@ExtendWith(MockKExtension::class)
class RentRepositoryAdapterTest {

    private lateinit var adapter: RentRepositoryAdapter

    private var mapper: RentMapper = RentMapper.INSTANCE

    private val sampleAccessionNumber = AccessionNumber("EEEE-254")
    private val sampleEmail = Email("mszewc@edu.pl")
    private val clientEntity = ClientEntity(UUID.randomUUID(), sampleEmail.value, true)
    private val productEntity = ProductEntity(UUID.randomUUID(), sampleAccessionNumber.value)


    @RelaxedMockK
    lateinit var rentRepository: IRepository<RentEntity>

    @RelaxedMockK
    lateinit var clientRepository: IRepository<ClientEntity>

    @RelaxedMockK
    lateinit var productRepository: IRepository<ProductEntity>

    @BeforeEach
    fun init() {
        adapter = RentRepositoryAdapter(rentRepository, productRepository, clientRepository, mapper)
    }

    @Test
    fun `Given valid accessionNumber, adapter should return proper active Rent`() {
        val entity = RentEntity(UUID.randomUUID(), UUID.randomUUID(), Date(), null, clientEntity, productEntity)
        every { rentRepository.find(any()) } returns entity
        val rent = adapter.findActiveByResourceId(sampleAccessionNumber)
        assertNotNull(rent)
        verify(exactly = 1) { rentRepository.find(any()) }
    }

    @Test
    fun `Given accessionNumber that not exist, adapter should return null`() {
        every { rentRepository.find(any()) } returns null
        val rent = adapter.findActiveByResourceId(sampleAccessionNumber)
        assertNull(rent)
        verify(exactly = 1) { rentRepository.find(any()) }
    }

    @Test
    fun `Given new Rent that accessionNumber and email exist, adapter should persist new Rent`() {
        val rent = Rent(UUID.randomUUID(), Date(), null, sampleEmail, sampleAccessionNumber)
        every { rentRepository.find(any()) } returns null
        every { clientRepository.find(any()) } returns clientEntity
        every { productRepository.find(any()) } returns productEntity
        adapter.save(rent)
        verify(exactly = 1) { rentRepository.find(any()) }
        verify(exactly = 1) { clientRepository.find(any()) }
        verify(exactly = 1) { productRepository.find(any()) }
        verify(exactly = 1) { rentRepository.add(any()) }
        verify(exactly = 0) { rentRepository.update(any()) }
    }

    @Test
    fun `Given valid Rent, adapter should update Rent`() {
        val entity = RentEntity(UUID.randomUUID(), UUID.randomUUID(), Date(), null, clientEntity, productEntity)
        val rent = Rent(UUID.randomUUID(), Date(), null, sampleEmail, sampleAccessionNumber)
        every { rentRepository.find(any()) } returns entity
        every { clientRepository.find(any()) } returns clientEntity
        every { productRepository.find(any()) } returns productEntity
        adapter.save(rent)
        verify(exactly = 1) { rentRepository.find(any()) }
        verify(exactly = 0) { rentRepository.add(any()) }
        verify(exactly = 1) { rentRepository.update(any()) }
    }

    @Test
    fun `Given valid id, adapter should return proper Rent`() {
        val guid = UUID.randomUUID()
        val entity = RentEntity(UUID.randomUUID(), guid, Date(), null, clientEntity, productEntity)
        every { rentRepository.find(any()) } returns entity
        val rent = adapter.getById(guid)
        assertNotNull(rent)
        verify(exactly = 1) { rentRepository.find(any()) }
    }

    @Test
    fun `Given id that not exist, adapter should return null`() {
        val guid = UUID.randomUUID()
        every { rentRepository.find(any()) } returns null
        val rent = adapter.getById(guid)
        assertNull(rent)
        verify(exactly = 1) { rentRepository.find(any()) }
    }
}