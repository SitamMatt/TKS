package edu.p.lodz.pl.pas.mvc.services;

import edu.p.lodz.pl.pas.mvc.model.User;
import edu.p.lodz.pl.pas.mvc.model.exceptions.LoginAlreadyTakenException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectAlreadyStoredException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectNotFoundException;
import edu.p.lodz.pl.pas.mvc.repositories.UsersRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class UsersService {
    @Inject
    private UsersRepository usersRepository;

    public List<User> getAllUsers() {
        return usersRepository.getAllUsers();
    }

    public void addUser(User user) {
        try {
            usersRepository.addUser(user);
        } catch (ObjectAlreadyStoredException | LoginAlreadyTakenException ignored) { }
    }

    public User findUser(String login) {
        return usersRepository.findUserByLogin(login);
    }

    public void updateUser(User user) {
        try {
            usersRepository.updateUser(user);
        } catch (ObjectNotFoundException ignored) { }
    }
}
