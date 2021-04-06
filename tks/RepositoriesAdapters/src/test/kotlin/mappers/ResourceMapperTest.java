package mappers;

import data.AbstractResourceEntity;
import data.BookEntity;
import data.MagazineEntity;
import lombok.SneakyThrows;
import domain.model.Book;
import domain.model.Resource;
import domain.model.values.AccessionNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ResourceMapperTest {

    ResourceMapper mapper;

    @BeforeEach
    public void init(){
        mapper = ResourceMapper.Companion.getINSTANCE();
    }

    @SneakyThrows
    @Test
    public void  BookToEntityTest(){
        Resource book = new Book(new AccessionNumber("EEEE-456"), "Diuna", "Frank Herbert");
        var entity = mapper.mapDomainObjectToEntity(book);
        assertNotNull(entity);
        assertTrue(entity instanceof BookEntity);
        assertEquals(Objects.requireNonNull(book.getAccessionNumber()).getValue(), entity.getAccessionNumber());
        assertEquals(book.getTitle(), entity.getTitle());
        assertNull(entity.getGuid());
        assertEquals(((Book)book).getAuthor(), ((BookEntity)entity).getAuthor());
    }

    @Test
    public void EntityToBookTest(){
        AbstractResourceEntity entity = new BookEntity("EEEE-456", "Diuna", "Frank Herbert");
        entity.setGuid(UUID.randomUUID());
        var book = mapper.mapEntityToDomainObject(entity);
        assertNotNull(book);
        assertTrue(book instanceof Book);
        assertEquals(entity.getAccessionNumber(), Objects.requireNonNull(book.getAccessionNumber()).getValue());
        assertEquals(entity.getTitle(), book.getTitle());
        assertEquals(((BookEntity)entity).getAuthor(), ((Book)book).getAuthor());
    }

    @SneakyThrows
    @Test
    public void BookToExistingEntityTest(){
        Resource book = new Book(new AccessionNumber("EEEE-456"), "Diuna", "Frank Herbert");
        AbstractResourceEntity entity = new BookEntity("EEEE-456", "Kolor magii", "Terry Pratchett");
        var guid = UUID.randomUUID();
        entity.setGuid(guid);
        mapper.mapDomainObjectToEntity(book, entity);
        assertEquals(Objects.requireNonNull(book.getAccessionNumber()).getValue(), entity.getAccessionNumber());
        assertEquals(book.getTitle(), entity.getTitle());
        assertEquals(((Book)book).getAuthor(), ((BookEntity)entity).getAuthor());
        assertEquals(guid, entity.getGuid());
    }

    @SneakyThrows
    @Test
    public void BookToExistingMagazineTest(){
        Resource book = new Book(new AccessionNumber("EEEE-456"), "Diuna", "Frank Herbert");
        AbstractResourceEntity entity = new MagazineEntity("EEEE-456", "Nature", "Nature Publishing Group");
        assertThrows(Exception.class, () -> mapper.mapDomainObjectToEntity(book, entity));
    }
}
