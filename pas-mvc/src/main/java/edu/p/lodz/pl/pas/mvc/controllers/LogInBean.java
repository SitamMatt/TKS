package edu.p.lodz.pl.pas.mvc.controllers;

import edu.p.lodz.pl.pas.mvc.services.UsersService;
import edu.p.lodz.pl.pas.mvc.services.dto.UserDto;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@RequestScoped
public class LogInBean implements Serializable {
    private static Logger logger = Logger.getLogger(LogInBean.class.getName());
    private String username;
    private String password;
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("edu.p.lodz.pl.pas.mvc.messages");

    @Inject
    UsersService usersService;

    public String login(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        UserDto user = usersService.find(username);
        try {
            request.login(username, password);
            logger.log(Level.INFO, username + " logged successfully");
            return "Start page";
        }
        catch (ServletException e){
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, resourceBundle.getString("login_failed"), null));
            logger.log(Level.WARNING, username + " tried to log in but provided wrong credentials");
            return null;
        }
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
