package edu.p.lodz.pl.pas.mvc.model;

import edu.p.lodz.pl.pas.mvc.Copyable;

import java.util.UUID;

public class User implements Copyable {
    boolean isActive;
    private UUID id;
    private UserRole role;
    private String firstName;
    private String lastName;
    private String login;
    private String password;

    public User(String firstName, String lastName, UserRole typ, String login, String password) {
        this.id = null;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = typ;
        this.login = login;
        this.password = password;
        this.isActive = false;
    }

    public User() {
        this.id = null;
    }

    public void map(User user) {
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.role = user.role;
        this.login = user.login;
        this.password = user.password;
        this.isActive = user.isActive;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public UUID getId() {
        return id;
    }

    public UserRole[] getPossibleRoles() {
        return UserRole.values();
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", typ=" + role +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", login='" + login + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}