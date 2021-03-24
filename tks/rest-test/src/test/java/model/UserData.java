package model;

public class UserData {
    private boolean active;
    private String firstname;
    private String lastname;
    private String guid;
    private String login;
    private String role;
    private String password;

    public UserData() {
    }

    public UserData(boolean active, String firstname, String lastname, String guid, String login, String role, String password) {
        this.active = active;
        this.firstname = firstname;
        this.lastname = lastname;
        this.guid = guid;
        this.login = login;
        this.role = role;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
