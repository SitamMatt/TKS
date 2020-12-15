package edu.p.lodz.pl.pas.mvc.controllers;

import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectLockedByRentException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectNotFoundException;
import edu.p.lodz.pl.pas.mvc.services.ResourcesService;
import edu.p.lodz.pl.pas.mvc.services.dto.ResourceDto;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

@RequestScoped
@Named
public class ResourcesController {
    @Inject
    private ResourcesService resourcesService;
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("edu.p.lodz.pl.pas.mvc.messages");

    public List<ResourceDto> listResources() {
        return resourcesService.getAllResources();
    }

    public String editResource() {
        return "ResourceEdit";
    }

    public String createResource() {
        return "ResourceCreate";
    }

    public void removeResource(String id) throws IOException {
        try {
            resourcesService.delete(UUID.fromString(id));
//        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());}
        } catch (ObjectLockedByRentException e) {
            FacesContext context = FacesContext.getCurrentInstance();
            String res = resourceBundle.getString("res_locked");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, res, "");
            context.addMessage(null, message);
        }
        catch (ObjectNotFoundException e){
            FacesContext context = FacesContext.getCurrentInstance();
            String res = resourceBundle.getString("not_found");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, res, "");
            context.addMessage(null, message);
        }
    }
}
