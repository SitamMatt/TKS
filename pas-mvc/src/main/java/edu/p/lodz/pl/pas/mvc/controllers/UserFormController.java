package edu.p.lodz.pl.pas.mvc.controllers;

import edu.p.lodz.pl.pas.mvc.model.Resource;
import edu.p.lodz.pl.pas.mvc.model.UserRole;
import edu.p.lodz.pl.pas.mvc.model.exceptions.LoginAlreadyTakenException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectAlreadyStoredException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectNotFoundException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.RepositoryException;
import edu.p.lodz.pl.pas.mvc.services.UsersService;
import edu.p.lodz.pl.pas.mvc.services.dto.UserDto;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ResourceBundle;

@ViewScoped
@Named
public class UserFormController implements Serializable {
    private final UserRole[] roles = UserRole.values();
    private String login;
    private UIComponent btn;
    @Inject
    private UsersService usersService;
    private UserDto user;
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("edu.p.lodz.pl.pas.mvc.messages");

    public UserRole[] getRoles() {
        return roles;
    }

    public UIComponent getBtn() {
        return btn;
    }

    public void setBtn(UIComponent btn) {
        this.btn = btn;
    }

    public UserDto getUser() {
        return user;
    }

    public void loadUser() {
        if (login != null) {
            user = usersService.find(login);
        }
    }

    @PostConstruct
    public void init() {
        user = new UserDto();
    }

    public String saveUser() {
        try {
            usersService.save(user);
            return "Users";
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
            FacesContext context = FacesContext.getCurrentInstance();
            String res = resourceBundle.getString("not_found");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, res, "");
            context.addMessage(null, message);
        } catch (ObjectAlreadyStoredException e) {
            e.printStackTrace();
            FacesContext context = FacesContext.getCurrentInstance();
            String res = resourceBundle.getString("object_stored");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, res, "");
            context.addMessage(null, message);
        } catch (LoginAlreadyTakenException e) {
            FacesContext context = FacesContext.getCurrentInstance();
            String res = resourceBundle.getString("login_already_taken");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, res, "");
            context.addMessage(null, message);
        } catch (RepositoryException e) {
            e.printStackTrace();
            FacesContext context = FacesContext.getCurrentInstance();
            String res = resourceBundle.getString("rep_exception");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, res, "");
            context.addMessage(null, message);
        }
        return "";
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
