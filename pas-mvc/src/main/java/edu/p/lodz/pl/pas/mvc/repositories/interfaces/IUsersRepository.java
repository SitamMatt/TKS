package edu.p.lodz.pl.pas.mvc.repositories.interfaces;

import edu.p.lodz.pl.pas.mvc.model.User;
import edu.p.lodz.pl.pas.mvc.model.exceptions.LoginAlreadyTakenException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectAlreadyStoredException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectNotFoundException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.RepositoryException;

import java.util.List;

public interface IUsersRepository extends IRepositoryBase<User> {
    User findUserByLogin(String login);
}
