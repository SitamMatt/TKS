package edu.p.lodz.pl.pas.mvc.model;

import java.util.UUID;

public class User {
    private UUID id;
    private Type typ;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    boolean isActive;

    public User(UUID id, String firstName, String lastName, Type typ, String login, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.typ = typ;
        this.login = login;
        this.password = password;
        this.isActive = false;
    }

    public UUID getId() {
        return id;
    }

    public Type getTyp(){
        return typ;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setTyp(Type typ){
        this.typ = typ;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setActive(boolean active) {
        isActive = active;
    }


}