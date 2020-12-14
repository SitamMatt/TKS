package edu.p.lodz.pl.pas.mvc.controllers;

import edu.p.lodz.pl.pas.mvc.services.UsersService;
import edu.p.lodz.pl.pas.mvc.services.dto.UserDto;

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

    public String summarizeUser() {
        return "UserSummary";
    }

    public List<UserDto> listUsers() throws CloneNotSupportedException {
        return usersService.getAllUsers();
    }
}
