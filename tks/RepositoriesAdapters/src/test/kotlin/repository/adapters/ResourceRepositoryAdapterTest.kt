//package repository.adapters
//
//import domain.model.context.library.Book
//import domain.model.values.AccessionNumber
//import io.mockk.every
//import io.mockk.impl.annotations.RelaxedMockK
//import io.mockk.junit5.MockKExtension
//import io.mockk.verify
//import org.junit.jupiter.api.Assertions.*
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.assertThrows
//import org.junit.jupiter.api.extension.ExtendWith
//import repository.data.AbstractResourceEntity
//import repository.data.BookEntity
//import repository.mappers.ResourceMapper
//import repository.repositories.IRepository
//import java.util.*
//
//@ExtendWith(MockKExtension::class)
//class ResourceRepositoryAdapterTest {
//
//    lateinit var adapter: ResourceRepositoryAdapter
//
//    var mapper: ResourceMapper = ResourceMapper.INSTANCE
//
//    @RelaxedMockK
//    lateinit var repository: IRepository<AbstractResourceEntity>
//
//    @BeforeEach
//    fun init() {
//        adapter = ResourceRepositoryAdapter(repository, mapper)
//    }
//
//    @Test
//    fun `Given valid accessionNumber, adapter should return a resource assigned to that accessionNumber`() {
//        every { repository.find(any()) } returns BookEntity(
//            UUID.randomUUID(),
//            "EEEE-254",
//            "Diuna",
//            "Frank Herbert"
//        )
//        val resource = adapter.findByAccessionNumber(AccessionNumber("EEEE-254"))
//        assertNotNull(resource!!)
//        verify(exactly = 1) { repository.find(any()) }
//        assertEquals("EEEE-254", resource.accessionNumber?.value)
//    }
//
//    @Test
//    fun `Given invalid accessionNumber, adapter should return null`() {
//        every { repository.find(any()) } returns null
//        assertNull(adapter.findByAccessionNumber(AccessionNumber("EEEE-254")))
//        verify(exactly = 1) { repository.find(any()) }
//    }
//
//    @Test
//    fun `Given existing Resource, adapter should remove it from repository`() {
//        val resource = Book(AccessionNumber("EEEE-254"), "Diuna", "Frank Herbert")
//        val entity = BookEntity(
//            UUID.randomUUID(),
//            "EEEE-254",
//            "Diuna",
//            "Frank Herbert"
//        )
//        every { repository.find(any()) } returns entity
//        assertDoesNotThrow { adapter.remove(resource) }
//        verify(exactly = 1) { repository.find(any()) }
//        verify(exactly = 1) { repository.remove(entity) }
//    }
//
//    @Test
//    fun `Given new Resource, adapter should throw when called remove`() {
//        val resource = Book(AccessionNumber("EEEE-254"), "Diuna", "Frank Herbert")
//        every { repository.find(any()) } returns null
//        assertThrows<Exception> { adapter.remove(resource) }
//        verify(exactly = 1) { repository.find(any()) }
//        verify(exactly = 0) { repository.remove(any()) }
//    }
//
//    @Test
//    fun `Given valid new Resource, adapter should persist new Resource in repository`() {
//        val resource = Book(AccessionNumber("EEEE-254"), "Diuna", "Frank Herbert")
//        every { repository.find(any()) } returns null
//        adapter.save(resource)
//        verify(exactly = 1) { repository.find(any()) }
//        verify(exactly = 1) { repository.add(any()) }
//        verify(exactly = 0) { repository.update(any()) }
//    }
//
//    @Test
//    fun `Given valid existing Resource, adapter should update Resource in repository`() {
//        val resource = Book(AccessionNumber("EEEE-254"), "Diuna", "Frank Herbert")
//        val entity = BookEntity(
//            UUID.randomUUID(),
//            "EEEE-254",
//            "Hyperion",
//            "Dan Simmons"
//        )
//        every { repository.find(any()) } returns entity
//        adapter.save(resource)
//        verify(exactly = 1) { repository.find(any()) }
//        verify(exactly = 0) { repository.add(any()) }
//        verify(exactly = 1) { repository.update(any()) }
//        assertEquals("Frank Herbert", entity.author)
//        assertEquals("Diuna", entity.title)
//    }
//}