package mappers;

import data.AbstractResourceEntity;
import data.BookEntity;
import data.MagazineEntity;
import model.Book;
import model.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ResourceMapperTest {

    ResourceMapper mapper;

    @BeforeEach
    public void init(){
        mapper = ResourceMapper.Companion.getINSTANCE();
    }

    @Test
    public void  BookToEntityTest(){
        Resource book = new Book(UUID.randomUUID(), "Diuna", "Frank Herbert");
        var entity = mapper.mapDomainObjectToEntity(book);
        assertNotNull(entity);
        assertTrue(entity instanceof BookEntity);
        assertEquals(book.getId(), entity.getId());
        assertEquals(book.getTitle(), entity.getTitle());
        assertNull(entity.getGuid());
        assertEquals(((Book)book).getAuthor(), ((BookEntity)entity).getAuthor());
    }

    @Test
    public void EntityToBookTest(){
        AbstractResourceEntity entity = new BookEntity(UUID.randomUUID(), "Diuna", "Frank Herbert");
        entity.setGuid(UUID.randomUUID());
        var book = mapper.mapEntityToDomainObject(entity);
        assertNotNull(book);
        assertTrue(book instanceof Book);
        assertEquals(entity.getId(), book.getId());
        assertEquals(entity.getTitle(), book.getTitle());
        assertEquals(((BookEntity)entity).getAuthor(), ((Book)book).getAuthor());
    }

    @Test
    public void BookToExistingEntityTest(){
        Resource book = new Book(UUID.randomUUID(), "Diuna", "Frank Herbert");
        AbstractResourceEntity entity = new BookEntity(UUID.randomUUID(), "Kolor magii", "Terry Pratchett");
        var guid = UUID.randomUUID();
        entity.setGuid(guid);
        mapper.mapDomainObjectToEntity(book, entity);
        assertEquals(book.getId(), entity.getId());
        assertEquals(book.getTitle(), entity.getTitle());
        assertEquals(((Book)book).getAuthor(), ((BookEntity)entity).getAuthor());
        assertEquals(guid, entity.getGuid());
    }

    @Test
    public void BookToExistingMagazineTest(){
        Resource book = new Book(UUID.randomUUID(), "Diuna", "Frank Herbert");
        AbstractResourceEntity entity = new MagazineEntity(UUID.randomUUID(), "Nature", "Nature Publishing Group");
        assertThrows(Exception.class, () -> mapper.mapDomainObjectToEntity(book, entity));
    }
}
