package repository.mappers

import domain.model.context.library.Book
import domain.model.context.library.Resource
import domain.model.values.AccessionNumber
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import repository.data.AbstractResourceEntity
import repository.data.BookEntity
import repository.data.MagazineEntity
import java.util.*

class ResourceMapperTest {
    private val mapper: ResourceMapper = ResourceMapper.INSTANCE

    @Test
    fun bookToEntityTest() {
        val book: Resource = Book(AccessionNumber("EEEE-456"), "Diuna", false, "Frank Herbert")
        val entity = mapper.mapDomainObjectToEntity(book)
        assertNotNull(entity!!)
        assertTrue(entity is BookEntity)
        assertEquals(book.accessionNumber?.value!!, entity.accessionNumber)
        assertEquals(book.title, entity.title)
        assertEquals(book.locked, entity.locked)
        assertNull(entity.guid)
        assertEquals((book as Book).author, (entity as BookEntity).author)
    }

    @Test
    fun nullableBookToEntity() {
        val entity = mapper.mapDomainObjectToEntity(null as Book?)
        assertNull(entity)
    }

    @Test
    fun entityToBookTest() {
        val entity: AbstractResourceEntity = BookEntity(UUID.randomUUID(), "EEEE-456", "Diuna", true, "Frank Herbert")
        val book = mapper.mapEntityToDomainObject(entity)
        assertNotNull(book!!)
        assertTrue(book is Book)
        assertEquals(entity.accessionNumber, book.accessionNumber?.value)
        assertEquals(entity.title, book.title)
        assertEquals(entity.locked, book.locked)
        assertEquals((entity as BookEntity).author, (book as Book).author)
    }

    @Test
    fun nullableEntityToBookTest() {
        assertNull(mapper.mapEntityToDomainObject(null as AbstractResourceEntity?))
    }

    @Test
    fun bookToExistingEntityTest() {
        val book: Resource = Book(AccessionNumber("EEEE-456"), "Diuna", true, "Frank Herbert")
        val guid = UUID.randomUUID()
        val entity: AbstractResourceEntity = BookEntity(guid, "EEEE-456", "Kolor magii", false, "Terry Pratchett")
        mapper.mapDomainObjectToEntity(book, entity)
        assertEquals(book.accessionNumber!!.value, entity.accessionNumber)
        assertEquals(book.title, entity.title)
        assertEquals(book.locked, entity.locked)
        assertEquals((book as Book).author, (entity as BookEntity).author)
        assertEquals(guid, entity.guid)
    }

    @Test
    fun nullableBookToExistingEntityTest(){
        val guid = UUID.randomUUID()
        val entity: AbstractResourceEntity = BookEntity(guid, "EEEE-456", "Kolor magii", true, "Terry Pratchett")
        mapper.mapDomainObjectToEntity(null as Resource?, entity)
        assertTrue(entity is BookEntity)
        assertEquals("EEEE-456", entity.accessionNumber)
        assertEquals("Kolor magii", entity.title)
        assertEquals(true, entity.locked)
        assertEquals("Terry Pratchett", (entity as BookEntity).author)
        assertEquals(guid, entity.guid)
    }

    @Test
    fun bookToExistingMagazineTest() {
        val book: Resource = Book(AccessionNumber("EEEE-456"), "Diuna", true, "Frank Herbert")
        val entity: AbstractResourceEntity = MagazineEntity(null, "EEEE-456", "Nature", true, "Nature Publishing Group")
        assertThrows<Exception> { mapper.mapDomainObjectToEntity(book, entity) }
    }
}