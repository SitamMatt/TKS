package edu.p.lodz.pl.pas.mvc.controllers;

import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectLockedByRentException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectNotFoundException;
import edu.p.lodz.pl.pas.mvc.services.ResourcesService;
import edu.p.lodz.pl.pas.mvc.services.dto.ResourceDto;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@ViewScoped
@Named
public class ResourcesController {
    @Inject
    private ResourcesService resourcesService;

    private String searchQuery;

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public List<ResourceDto> listResources() {
        if(searchQuery != null) {
            ResourceDto resourceDto = resourcesService.getAllResources().stream()
                    .filter(x -> x.getId().toString().equals(searchQuery))
                    .findFirst()
                    .orElse(null);
            if (resourceDto == null) {
                return new ArrayList<>();
            }
            return Collections.singletonList(resourceDto);
        }
        return resourcesService.getAllResources();
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
