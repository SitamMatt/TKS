package edu.p.lodz.pl.pas.mvc.controllers;

import edu.p.lodz.pl.pas.mvc.services.UsersService;
import edu.p.lodz.pl.pas.mvc.services.dto.UserDto;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ViewScoped
@Named
public class UsersController implements Serializable {

    public List<UserDto> getUsers() {
        return users;
    }

    private List<UserDto> users;

    @PostConstruct
    public void init(){
        this.users = usersService.getAllUsers();
    }

    public void loadUsers(){
        if(searchQuery != null) {
            UserDto user = usersService.find(searchQuery);
            if(user.getId() == null) {
                users =  new ArrayList<>();
            }else{
                users =  Collections.singletonList(user);
            }
        }
    }

    @Inject
    private UsersService usersService;

    private String searchQuery;

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public String editUser() {
        return "UserEdit";
    }

    public String createUser() {
        return "UserCreate";
    }

    public String summarizeUser() {
        return "UserSummary";
    }

//    public List<UserDto> listUsers() throws CloneNotSupportedException {
//        if(searchQuery != null) {
//            UserDto user = usersService.find(searchQuery);
//            if(user.getId() == null) {
//                return new ArrayList<>();
//            }
//            return Collections.singletonList(user);
//        }
//        return usersService.getAllUsers();
//    }

    public String search() {
        return "usersList.xhtml?faces-redirect=true&searchQuery=" + searchQuery;
    }
}
