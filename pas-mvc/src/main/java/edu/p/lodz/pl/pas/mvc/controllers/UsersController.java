package edu.p.lodz.pl.pas.mvc.controllers;

import edu.p.lodz.pl.pas.mvc.model.InMemoryUsersRepository;
import edu.p.lodz.pl.pas.mvc.model.User;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectAlreadyStoredException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectNotFoundException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@RequestScoped
@Named
public class UsersController {

    @Inject
    private InMemoryUsersRepository usersRepository;

    private List<User> currentUsers;

    private User newUser = new User();
    private User editCopyUser;

    public User getNewUser(){
        return newUser;
    }

    public String editUserProcess(String login){
        newUser = usersRepository.findUserByLogin(login); //todo: id changing while editing user
        return "EditPersonProcess";
    }

    public void confirmEditUser(User user) throws ObjectNotFoundException {
        usersRepository.updateUser(user);
    }


    public InMemoryUsersRepository getUsersRepository(){
        return usersRepository;
    }

    public void confirmNewUser() throws ObjectAlreadyStoredException {
        usersRepository.addUser(newUser);
        System.out.println(usersRepository);
    }

}
