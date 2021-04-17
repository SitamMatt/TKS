package webservice.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LibraryItemSoapDto {
    private String accessionNumber;
    private String title;
    private String author;
    private String publisher;
    private String type;
}
