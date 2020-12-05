package edu.p.lodz.pl.pas.mvc.services;

import edu.p.lodz.pl.pas.mvc.model.User;
import edu.p.lodz.pl.pas.mvc.model.exceptions.LoginAlreadyTakenException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectAlreadyStoredException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectNotFoundException;
import edu.p.lodz.pl.pas.mvc.repositories.IUsersRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class UsersService {
    @Inject
    private IUsersRepository usersRepository;

    public List<User> getAllUsers() throws CloneNotSupportedException {
        return usersRepository.getAll();
    }

    public User find(String login) {
        return usersRepository.findUserByLogin(login);
    }

    public void Save(User user) throws ObjectNotFoundException, ObjectAlreadyStoredException, LoginAlreadyTakenException {
        if (usersRepository.has(user)) {
            usersRepository.update(user);
        } else {
            usersRepository.add(user);
        }
    }
}
