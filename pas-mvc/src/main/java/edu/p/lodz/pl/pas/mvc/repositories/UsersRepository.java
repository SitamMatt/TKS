package edu.p.lodz.pl.pas.mvc.repositories;

import edu.p.lodz.pl.pas.mvc.model.User;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectAlreadyStoredException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectNotFoundException;

import java.util.List;

public interface UsersRepository {
    void addUser(User user) throws ObjectAlreadyStoredException;

    List<User> getAllUsers();

    User findUserByLogin(String login);

    void updateUser(User user) throws ObjectNotFoundException;
}
