package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibraryItemDto {
    private String accessionNumber;
    private String title;
    private String author;
    private String publisher;
    private String type;

    public String getType() {
        return type;
    }
}
