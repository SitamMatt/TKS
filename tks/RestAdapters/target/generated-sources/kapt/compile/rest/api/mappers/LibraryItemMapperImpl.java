package rest.api.mappers;

import common.mappers.AccessionNumberMapper;
import domain.model.Book;
import domain.model.Magazine;
import domain.model.values.AccessionNumber;
import javax.annotation.processing.Generated;
import org.mapstruct.factory.Mappers;
import rest.api.dto.LibraryItemDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-04-18T01:30:58+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.9 (Oracle Corporation)"
)
public class LibraryItemMapperImpl implements LibraryItemMapper {

    private final AccessionNumberMapper accessionNumberMapper = Mappers.getMapper( AccessionNumberMapper.class );

    @Override
    public Book toBook(LibraryItemDto source) {
        if ( source == null ) {
            return null;
        }

        AccessionNumber accessionNumber = null;
        String title = null;
        String author = null;

        accessionNumber = accessionNumberMapper.toAccessionNumber( source.getAccessionNumber() );
        title = source.getTitle();
        author = source.getAuthor();

        Book book = new Book( accessionNumber, title, author );

        return book;
    }

    @Override
    public Magazine toMagazine(LibraryItemDto source) {
        if ( source == null ) {
            return null;
        }

        AccessionNumber accessionNumber = null;
        String title = null;
        String publisher = null;

        accessionNumber = accessionNumberMapper.toAccessionNumber( source.getAccessionNumber() );
        title = source.getTitle();
        publisher = source.getPublisher();

        Magazine magazine = new Magazine( accessionNumber, title, publisher );

        return magazine;
    }

    @Override
    public LibraryItemDto toDto(Book source) {
        if ( source == null ) {
            return null;
        }

        LibraryItemDto libraryItemDto = new LibraryItemDto();

        libraryItemDto.setAccessionNumber( accessionNumberMapper.toString( source.getAccessionNumber() ) );
        libraryItemDto.setTitle( source.getTitle() );
        libraryItemDto.setAuthor( source.getAuthor() );

        return libraryItemDto;
    }

    @Override
    public LibraryItemDto toDto(Magazine source) {
        if ( source == null ) {
            return null;
        }

        LibraryItemDto libraryItemDto = new LibraryItemDto();

        libraryItemDto.setAccessionNumber( accessionNumberMapper.toString( source.getAccessionNumber() ) );
        libraryItemDto.setTitle( source.getTitle() );
        libraryItemDto.setPublisher( source.getPublisher() );

        return libraryItemDto;
    }
}
