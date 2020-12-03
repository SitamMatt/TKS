package edu.p.lodz.pl.pas.mvc.controllers;

import edu.p.lodz.pl.pas.mvc.repositories.InMemoryUsersRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
@Named
public class UsersController {

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
