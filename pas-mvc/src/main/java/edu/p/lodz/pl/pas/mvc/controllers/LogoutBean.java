package edu.p.lodz.pl.pas.mvc.controllers;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
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
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() + "/common_pages/welcome.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUsername(){
        return FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
    }
}
