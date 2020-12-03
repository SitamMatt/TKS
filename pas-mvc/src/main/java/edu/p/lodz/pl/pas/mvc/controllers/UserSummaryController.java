package edu.p.lodz.pl.pas.mvc.controllers;

import edu.p.lodz.pl.pas.mvc.model.User;
import edu.p.lodz.pl.pas.mvc.repositories.InMemoryUsersRepository;
import edu.p.lodz.pl.pas.mvc.services.UsersService;

import javax.annotation.security.RolesAllowed;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
@RolesAllowed({"ADMIN", "WORKER"})
public class UserSummaryController implements Serializable {
    @Inject
    private InMemoryUsersRepository usersRepository;
    @Inject
    private UsersService usersService;
    private String login;
    private User user;

    public String getLogin(){
        return login;
    }

    public void setLogin(String login){
        this.login = login;
    }

    public User getUser(){
        return user;
    }

    public void init(){
        if (login != null)
            user = usersRepository.findUserByLogin(login);
    }
}
