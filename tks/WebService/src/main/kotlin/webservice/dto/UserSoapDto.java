package webservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserSoapDto {
    private String email;
    private boolean active;
    private String role;
}
