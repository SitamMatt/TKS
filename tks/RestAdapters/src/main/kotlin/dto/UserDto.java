package dto;

import lombok.Data;

import javax.json.bind.annotation.JsonbTransient;

@Data
public class UserDto {
    private String email;
    private String password;
    private boolean active;
    private String role;

    @JsonbTransient
    public String getPassword() {
        return password;
    }
}
