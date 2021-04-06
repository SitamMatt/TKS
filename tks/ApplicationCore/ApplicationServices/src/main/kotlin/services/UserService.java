package services;

import domain.model.values.Email;
import ports.primary.IUserService;
import ports.secondary.UserSearchPort;
import ports.secondary.UserPersistencePort;
import domain.exceptions.DuplicatedEmailException;
import domain.exceptions.UserNotFoundException;
import domain.model.User;
import domain.model.UserRole;


public class UserService implements IUserService {

    private final UserPersistencePort userPersistencePort;
    private final UserSearchPort userSearchPort;

    public UserService(UserPersistencePort userPersistencePort, UserSearchPort userSearchPort) {
        this.userPersistencePort = userPersistencePort;
        this.userSearchPort = userSearchPort;
    }

    public void register(User user) throws DuplicatedEmailException {
        var duplicate = userSearchPort.findByEmail(user.getEmail());
        if(duplicate != null) throw new DuplicatedEmailException();
        userPersistencePort.add(user);
    }

    public void changeRole(Email email, UserRole role) throws UserNotFoundException {
        var user = userSearchPort.findByEmail(email);
        if(user == null) throw new UserNotFoundException();
        if(!user.getRole().equals(role)){
            user.setRole(role);
            userPersistencePort.update(user);
        }
    }

    public void changeState(Email email, boolean state) throws UserNotFoundException {
        var user = userSearchPort.findByEmail(email);
        if(user == null) throw new UserNotFoundException();
        if(!user.getActive() == state){
            user.setActive(state);
            userPersistencePort.update(user);
        }
    }

    public User getDetails(Email email) throws UserNotFoundException {
        var user = userSearchPort.findByEmail(email);
        if(user == null) throw new UserNotFoundException();
        return user;
    }
}
