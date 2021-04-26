//package repository.adapters
//
//import domain.model.Rent
//import domain.model.values.AccessionNumber
//import domain.model.values.Email
//import io.mockk.every
//import io.mockk.impl.annotations.RelaxedMockK
//import io.mockk.junit5.MockKExtension
//import io.mockk.verify
//import org.junit.jupiter.api.Assertions.assertNotNull
//import org.junit.jupiter.api.Assertions.assertNull
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.extension.ExtendWith
//import repository.data.AbstractResourceEntity
//import repository.data.BookEntity
//import repository.data.RentEntity
//import repository.data.UserEntity
//import repository.mappers.RentMapper
//import repository.repositories.IRepository
//import java.util.*
//
//@ExtendWith(MockKExtension::class)
//class RentRepositoryAdapterTest {
//
//    lateinit var adapter: RentRepositoryAdapter
//
//    var mapper: RentMapper = RentMapper.INSTANCE
//
//    @RelaxedMockK
//    lateinit var rentRepository: IRepository<RentEntity>
//
//    @RelaxedMockK
//    lateinit var userRepository: IRepository<UserEntity>
//
//    @RelaxedMockK
//    lateinit var resourceRepository: IRepository<AbstractResourceEntity>
//
//    @BeforeEach
//    fun init() {
//        adapter = RentRepositoryAdapter(rentRepository, resourceRepository, userRepository, mapper)
//    }
//
//    @Test
//    fun `Given valid accessionNumber, adapter should return proper active Rent`() {
//        val userEntity = UserEntity(UUID.randomUUID(), "mszewc@edu.pl", "ADMIN", "password", true)
//        val resourceEntity = BookEntity(UUID.randomUUID(), "EEEE-254", "Diuna", "Frank Herbert")
//        val entity = RentEntity(UUID.randomUUID(), UUID.randomUUID(), Date(), null, userEntity, resourceEntity)
//        every { rentRepository.find(any()) } returns entity
//        val rent = adapter.findActiveByResourceId(AccessionNumber("EEEE-254"))
//        assertNotNull(rent)
//        verify(exactly = 1) { rentRepository.find(any()) }
//    }
//
//    @Test
//    fun `Given accessionNumber that not exist, adapter should return null`() {
//        every { rentRepository.find(any()) } returns null
//        val rent = adapter.findActiveByResourceId(AccessionNumber("EEEE-254"))
//        assertNull(rent)
//        verify(exactly = 1) { rentRepository.find(any()) }
//    }
//
//    @Test
//    fun `Given new Rent that accessionNumber and email exist, adapter should persist new Rent`() {
//        val userEntity = UserEntity(UUID.randomUUID(), "mszewc@edu.pl", "ADMIN", "password", true)
//        val resourceEntity = BookEntity(UUID.randomUUID(), "EEEE-254", "Diuna", "Frank Herbert")
//        val rent = Rent(UUID.randomUUID(), Date(), null, Email("mszewc@edu.pl"), AccessionNumber("EEEE-254"))
//        every { rentRepository.find(any()) } returns null
//        every { userRepository.find(any()) } returns userEntity
//        every { resourceRepository.find(any()) } returns resourceEntity
//        adapter.save(rent)
//        verify(exactly = 1) { rentRepository.find(any()) }
//        verify(exactly = 1) { userRepository.find(any()) }
//        verify(exactly = 1) { resourceRepository.find(any()) }
//        verify(exactly = 1) { rentRepository.add(any()) }
//        verify(exactly = 0) { rentRepository.update(any()) }
//    }
//
//    @Test
//    fun `Given valid Rent, adapter should update Rent`() {
//        val userEntity = UserEntity(UUID.randomUUID(), "mszewc@edu.pl", "ADMIN", "password", true)
//        val resourceEntity = BookEntity(UUID.randomUUID(), "EEEE-254", "Diuna", "Frank Herbert")
//        val entity = RentEntity(UUID.randomUUID(), UUID.randomUUID(), Date(), null, userEntity, resourceEntity)
//        val rent = Rent(UUID.randomUUID(), Date(), null, Email("mszewc@edu.pl"), AccessionNumber("EEEE-254"))
//        every { rentRepository.find(any()) } returns entity
//        every { userRepository.find(any()) } returns userEntity
//        every { resourceRepository.find(any()) } returns resourceEntity
//        adapter.save(rent)
//        verify(exactly = 1) { rentRepository.find(any()) }
//        verify(exactly = 0) { rentRepository.add(any()) }
//        verify(exactly = 1) { rentRepository.update(any()) }
//    }
//
//    @Test
//    fun `Given valid id, adapter should return proper Rent`() {
//        val guid = UUID.randomUUID()
//        val userEntity = UserEntity(UUID.randomUUID(), "mszewc@edu.pl", "ADMIN", "password", true)
//        val resourceEntity = BookEntity(UUID.randomUUID(), "EEEE-254", "Diuna", "Frank Herbert")
//        val entity = RentEntity(UUID.randomUUID(), guid, Date(), null, userEntity, resourceEntity)
//        every { rentRepository.find(any()) } returns entity
//        val rent = adapter.getById(guid)
//        assertNotNull(rent)
//        verify(exactly = 1) { rentRepository.find(any()) }
//    }
//
//    @Test
//    fun `Given id that not exist, adapter should return null`() {
//        val guid = UUID.randomUUID()
//        every { rentRepository.find(any()) } returns null
//        val rent = adapter.getById(guid)
//        assertNull(rent)
//        verify(exactly = 1) { rentRepository.find(any()) }
//    }
//}