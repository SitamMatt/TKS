package model;

public enum UserRole {
    ADMIN, WORKER, CLIENT;

    public static UserRole fromString(String role) {
        if (role.equalsIgnoreCase("ADMIN")) {
            return ADMIN;
        } else if (role.equalsIgnoreCase("WORKER")) {
            return WORKER;
        } else {
            return CLIENT;
        }
    }
}
