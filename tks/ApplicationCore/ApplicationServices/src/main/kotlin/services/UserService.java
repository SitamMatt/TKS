package services;

import exceptions.DuplicatedEmailException;
import exceptions.UserNotFoundException;
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

    public void register(User user) throws DuplicatedEmailException {
        var duplicate = userFilterPort.findByEmail(user.getEmail());
        if(duplicate != null) throw new DuplicatedEmailException();
        userSavePort.add(user);
    }

    public void changeRole(String email, UserRole role) throws UserNotFoundException {
        var user = userFilterPort.findByEmail(email);
        if(user == null) throw new UserNotFoundException();
        if(!user.getRole().equals(role)){
            user.setRole(role);
            userSavePort.update(user);
        }
    }

    public void changeState(String email, boolean state) throws UserNotFoundException {
        var user = userFilterPort.findByEmail(email);
        if(user == null) throw new UserNotFoundException();
        if(!user.getActive() == state){
            user.setActive(state);
            userSavePort.update(user);
        }
    }

    public User getDetails(String email) throws UserNotFoundException {
        var user = userFilterPort.findByEmail(email);
        if(user == null) throw new UserNotFoundException();
        return user;
    }
}
