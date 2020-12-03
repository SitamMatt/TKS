package edu.p.lodz.pl.pas.mvc.controllers;

import edu.p.lodz.pl.pas.mvc.repositories.InMemoryUsersRepository;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
@Named
@RolesAllowed("ADMIN")
@Stateful
public class UsersController {

    public UsersController(){
        boolean is = FacesContext.getCurrentInstance().getExternalContext().isUserInRole("ADMIN");
    }

    @Inject
    private InMemoryUsersRepository usersRepository;

    public String editUser() {
        return "UserEdit";
    }

    public String createUser() {
        return "UserCreate";
    }

    public InMemoryUsersRepository getUsersRepository() {
        return usersRepository;
    }
}
