package rest.api.mappers;

import common.mappers.AccessionNumberMapper;
import data.BookEntity;
import data.MagazineEntity;
import javax.annotation.processing.Generated;
import domain.model.Book;
import domain.model.Magazine;
import domain.model.values.AccessionNumber;
import org.mapstruct.factory.Mappers;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-04-07T00:42:33+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.9 (Oracle Corporation)"
)
public class ResourceMapperImpl implements ResourceMapper {

    private final AccessionNumberMapper accessionNumberMapper = Mappers.getMapper( AccessionNumberMapper.class );

    @Override
    public Book mapEntityToDomainObject(BookEntity entity) {
        if ( entity == null ) {
            return null;
        }

        AccessionNumber accessionNumber = null;
        String title = null;
        String author = null;

        accessionNumber = accessionNumberMapper.toAccessionNumber( entity.getAccessionNumber() );
        title = entity.getTitle();
        author = entity.getAuthor();

        Book book = new Book( accessionNumber, title, author );

        return book;
    }

    @Override
    public BookEntity mapDomainObjectToEntity(Book resource) {
        if ( resource == null ) {
            return null;
        }

        String accessionNumber = null;
        String title = null;
        String author = null;

        accessionNumber = accessionNumberMapper.toString( resource.getAccessionNumber() );
        title = resource.getTitle();
        author = resource.getAuthor();

        BookEntity bookEntity = new BookEntity( accessionNumber, title, author );

        return bookEntity;
    }

    @Override
    public void mapDomainObjectToEntity(Book resource, BookEntity entity) {
        if ( resource == null ) {
            return;
        }

        entity.setAccessionNumber( accessionNumberMapper.toString( resource.getAccessionNumber() ) );
        entity.setTitle( resource.getTitle() );
        entity.setAuthor( resource.getAuthor() );
    }

    @Override
    public Magazine mapEntityToDomainObject(MagazineEntity entity) {
        if ( entity == null ) {
            return null;
        }

        AccessionNumber accessionNumber = null;
        String title = null;
        String publisher = null;

        accessionNumber = accessionNumberMapper.toAccessionNumber( entity.getAccessionNumber() );
        title = entity.getTitle();
        publisher = entity.getPublisher();

        Magazine magazine = new Magazine( accessionNumber, title, publisher );

        return magazine;
    }

    @Override
    public MagazineEntity mapDomainObjectToEntity(Magazine resource) {
        if ( resource == null ) {
            return null;
        }

        String accessionNumber = null;
        String title = null;
        String publisher = null;

        accessionNumber = accessionNumberMapper.toString( resource.getAccessionNumber() );
        title = resource.getTitle();
        publisher = resource.getPublisher();

        MagazineEntity magazineEntity = new MagazineEntity( accessionNumber, title, publisher );

        return magazineEntity;
    }

    @Override
    public void mapDomainObjectToEntity(Magazine resource, MagazineEntity entity) {
        if ( resource == null ) {
            return;
        }

        entity.setAccessionNumber( accessionNumberMapper.toString( resource.getAccessionNumber() ) );
        entity.setTitle( resource.getTitle() );
        entity.setPublisher( resource.getPublisher() );
    }
}
