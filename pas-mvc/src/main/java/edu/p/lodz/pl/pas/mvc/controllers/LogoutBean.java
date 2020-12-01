package edu.p.lodz.pl.pas.mvc.controllers;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

@Named
@SessionScoped
public class LogoutBean implements Serializable {
    public void logout(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("auth/login.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
