package edu.p.lodz.pl.pas.mvc.controllers;

import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectLockedByRentException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectNotFoundException;
import edu.p.lodz.pl.pas.mvc.services.ResourcesService;
import edu.p.lodz.pl.pas.mvc.services.dto.ResourceDto;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

@ViewScoped
@Named
public class ResourcesController implements Serializable {
    @Inject
    private ResourcesService resourcesService;

    private String searchQuery;

    private List<ResourceDto> reses;
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("edu.p.lodz.pl.pas.mvc.messages");

    @PostConstruct
    public void init(){
        this.reses = resourcesService.getAllResources();
    }

    public List<ResourceDto> getReses() {
        return reses;
    }

    public void loadResources(){
        if(searchQuery != null) {
            ResourceDto resourceDto = resourcesService.getAllResources().stream()
                    .filter(x -> x.getId().toString().equals(searchQuery))
                    .findFirst()
                    .orElse(null);
            if (resourceDto == null) {
                reses = new ArrayList<>();
            }else{
                reses = Collections.singletonList(resourceDto);
            }
        }
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public String editResource() {
        return "ResourceEdit";
    }

    public String createResource() {
        return "ResourceCreate";
    }

    public void removeResource(ActionEvent event) throws IOException {
        try{
            UUID value = (UUID) event.getComponent().getAttributes().get("selected");
            resourcesService.delete(value);
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        }
        catch (ObjectLockedByRentException e){
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

    public String searchActive() {
        return "resourcesList.xhtml?faces-redirect=true&searchQuery=" + searchQuery;
    }
}
