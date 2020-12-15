package edu.p.lodz.pl.pas.mvc.controllers;

import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectLockedByRentException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectNotFoundException;
import edu.p.lodz.pl.pas.mvc.services.ResourcesService;
import edu.p.lodz.pl.pas.mvc.services.dto.ResourceDto;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@ViewScoped
@Named
public class ResourcesController implements Serializable {
    @Inject
    private ResourcesService resourcesService;

    private String searchQuery;

    private List<ResourceDto> reses;

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

    public void removeResource(ActionEvent event) throws IOException, ObjectLockedByRentException, ObjectNotFoundException {
        UUID value = (UUID) event.getComponent().getAttributes().get("selected");
        resourcesService.delete(value);
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }

    public String searchActive() {
        return "resourcesList.xhtml?faces-redirect=true&searchQuery=" + searchQuery;
    }
}
