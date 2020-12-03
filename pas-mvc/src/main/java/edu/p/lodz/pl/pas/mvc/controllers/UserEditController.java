package edu.p.lodz.pl.pas.mvc.controllers;

import edu.p.lodz.pl.pas.mvc.model.User;
import edu.p.lodz.pl.pas.mvc.model.exceptions.LoginAlreadyTakenException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectAlreadyStoredException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectNotFoundException;
import edu.p.lodz.pl.pas.mvc.repositories.InMemoryUsersRepository;
import edu.p.lodz.pl.pas.mvc.services.UsersService;

import javax.annotation.PostConstruct;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named
@RolesAllowed("ADMIN")
@Stateful
public class UserEditController implements Serializable {
    private String login;

    @Inject
    private InMemoryUsersRepository usersRepository;
    @Inject
    private UsersService usersService;
    private User newUser;

    @PostConstruct
    public void init() {
        if (login == null) {
            newUser = new User();
        } else {
            newUser = usersRepository.findUserByLogin(login);
            login = null;
        }
    }

    public void saveUser() {
        try {
            usersService.Save(newUser);
        } catch (ObjectNotFoundException | ObjectAlreadyStoredException | LoginAlreadyTakenException e) {
            e.printStackTrace();
        } finally {
            newUser = new User();
            login = null;
        }
    }

    public User getNewUser() {
        return newUser;
    }

    public void setNewUser(User newUser) {
        this.newUser = newUser;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
