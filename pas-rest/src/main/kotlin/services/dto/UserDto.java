package services.dto;


import model.UserRole;

import java.util.UUID;

public class UserDto {
    private UUID id;
    boolean isActive;
    private UserRole role;
    private String firstName;
    private String lastName;
    private String login;
    private String password;

    public UserDto(UUID id, boolean isActive, UserRole role, String firstName, String lastName, String login, String password) {
        this.id = id;
        this.isActive = isActive;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
    }

    public UserDto(){}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
