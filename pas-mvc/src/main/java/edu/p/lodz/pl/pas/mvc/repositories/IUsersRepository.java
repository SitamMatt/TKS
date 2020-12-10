package edu.p.lodz.pl.pas.mvc.repositories;

import edu.p.lodz.pl.pas.mvc.model.User;
import edu.p.lodz.pl.pas.mvc.model.exceptions.LoginAlreadyTakenException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectAlreadyStoredException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectNotFoundException;

import java.util.List;

public interface IUsersRepository {
    boolean has(User user);

    void add(User user) throws ObjectAlreadyStoredException, LoginAlreadyTakenException, ObjectNotFoundException;

    List<User> getAll() throws CloneNotSupportedException;

    User findUserByLogin(String login);

    void update(User user) throws ObjectNotFoundException, LoginAlreadyTakenException;
}
