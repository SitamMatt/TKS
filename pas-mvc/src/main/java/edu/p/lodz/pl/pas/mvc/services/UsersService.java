package edu.p.lodz.pl.pas.mvc.services;

import edu.p.lodz.pl.pas.mvc.model.User;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectAlreadyStoredException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectNotFoundException;
import edu.p.lodz.pl.pas.mvc.repositories.InMemoryUsersRepository;
import edu.p.lodz.pl.pas.mvc.repositories.UsersRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;

@RequestScoped
public class UsersService {
    @Inject
    private InMemoryUsersRepository usersRepository;

    public List<User> getAllUsers() {
        return usersRepository.getAllUsers();
    }

    public void addUser(User user) {
        try {
            usersRepository.addUser(user);
        } catch (ObjectAlreadyStoredException ignored) { }
    }

    public User findUser(String login) {
        return usersRepository.findUserByLogin(login);
    }

    public void updateUser(User user) {
        try {
            usersRepository.updateUser(user);
        } catch (ObjectNotFoundException ignored) { }
    }

    public void Save(User newUser) throws ObjectNotFoundException, ObjectAlreadyStoredException {
        if(usersRepository.getAllUsers().stream().anyMatch(x -> x.getId() == newUser.getId())){
            usersRepository.updateUser(newUser);
        }else{
            usersRepository.addUser(newUser);
        }
    }
}
