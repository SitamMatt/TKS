package edu.p.lodz.pl.pas.mvc.controllers;

import edu.p.lodz.pl.pas.mvc.model.User;
import edu.p.lodz.pl.pas.mvc.services.UsersService;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@RequestScoped
@Named
public class UsersController {

    @Inject
    private UsersService usersService;

    public String editUser() {
        return "UserEdit";
    }

    public String createUser() {
        return "UserCreate";
    }

    public List<User> listUsers() throws CloneNotSupportedException {
        return usersService.getAllUsers();
    }
}
