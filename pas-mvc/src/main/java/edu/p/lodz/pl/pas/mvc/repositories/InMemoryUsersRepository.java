package edu.p.lodz.pl.pas.mvc.repositories;

import edu.p.lodz.pl.pas.mvc.model.User;
import edu.p.lodz.pl.pas.mvc.model.exceptions.LoginAlreadyTakenException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectAlreadyStoredException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectNotFoundException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.copy;

@ApplicationScoped
public class InMemoryUsersRepository implements UsersRepository {
    private List<User> users;
    @Inject
    private UsersFiller usersFiller;

    @PostConstruct
    public void usersInit() {
        users = usersFiller.fillUsers();
    }

    @Override
    public void addUser(User user) throws ObjectAlreadyStoredException, LoginAlreadyTakenException {
        if(findUserByLogin(user.getLogin()) != null) {
            throw new ObjectAlreadyStoredException();
        }

        if(users.stream().anyMatch((u) -> u.getLogin().equals(user.getLogin()))) {
            throw new LoginAlreadyTakenException();
        }
        users.add(user);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        copy(list, users);
        return list;
    }

    @Override
    public User findUserByLogin(String login) {
        return users.stream()
                .filter(x -> x.getLogin().equals(login))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void updateUser(User user) throws ObjectNotFoundException {
        if(findUserByLogin(user.getLogin()) == null) {
            throw new ObjectNotFoundException();
        }
        users.removeIf(x -> x.getLogin().equals(user.getLogin()));
        users.add(user);
    }
}
