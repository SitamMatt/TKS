package edu.p.lodz.pl.pas.mvc.model;

import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectAlreadyStoredException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectNotFoundException;

import java.util.List;

public interface IUsersRepository {
    void addUser(User user) throws ObjectAlreadyStoredException;

    List<User> getAllUsers();

    User findUserByLogin(String login);

    void updateUser(User user) throws ObjectNotFoundException;
}
