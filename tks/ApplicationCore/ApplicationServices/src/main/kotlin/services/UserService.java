package services;

import interfaces.UserFilterPort;
import interfaces.UserSavePort;
import model.User;
import model.UserRole;


public class UserService {

    UserSavePort userSavePort;
    UserFilterPort userFilterPort;

    public UserService() {
    }

    public UserService(UserSavePort userSavePort, UserFilterPort userFilterPort) {
        this.userSavePort = userSavePort;
        this.userFilterPort = userFilterPort;
    }

    public void register(User user){

    }

    public void changeRole(String email, UserRole role){

    }

    public void changeState(String email, boolean state){

    }

    public User getDetails(String email){
        return null;
    }
}
