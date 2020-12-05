package edu.p.lodz.pl.pas.mvc.controllers;

import edu.p.lodz.pl.pas.mvc.model.User;
import edu.p.lodz.pl.pas.mvc.model.exceptions.LoginAlreadyTakenException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectAlreadyStoredException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectNotFoundException;
import edu.p.lodz.pl.pas.mvc.services.UsersService;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named
@RolesAllowed("ADMIN")
@Stateful
public class UserFormController implements Serializable {
    private String login;

    public UIComponent getBtn() {
        return btn;
    }

    public void setBtn(UIComponent btn) {
        this.btn = btn;
    }

    private UIComponent btn;

    @Inject
    private UsersService usersService;
    private User newUser;

    @PostConstruct
    public void init() {
        if (login == null) {
            newUser = new User();
        } else {
            newUser = usersService.find(login);
            login = null;
        }
    }

    public void saveUser() {
        try {
            usersService.Save(newUser);
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
        } catch (ObjectAlreadyStoredException e) {
            e.printStackTrace();
        } catch (LoginAlreadyTakenException e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid password length", "");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(btn.getClientId(context), message);
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
