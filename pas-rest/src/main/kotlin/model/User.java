package model;

import java.util.UUID;

public class User extends Entity {
    boolean isActive;
    private UserRole role;
    private String firstName;
    private String lastName;
    private String login;
    private String password;

    public User(UUID id) {
        super(id);
    }

    public User(UUID id, boolean isActive, UserRole role, String firstName, String lastName, String login, String password) {
        super(id);
        this.isActive = isActive;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
    }

    public void map(User user) {
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.role = user.role;
        this.login = user.login;
        this.password = user.password;
        this.isActive = user.isActive;
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
}