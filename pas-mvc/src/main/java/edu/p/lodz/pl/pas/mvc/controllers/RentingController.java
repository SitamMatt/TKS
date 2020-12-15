package edu.p.lodz.pl.pas.mvc.controllers;

import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectAlreadyStoredException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.RepositoryException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ResourceNotAvailableException;
import edu.p.lodz.pl.pas.mvc.services.EventsService;
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
public class RentingController implements Serializable {
    private List<ResourceDto> availableResources;
    @Inject
    private EventsService eventsService;
    @Inject
    private ResourcesService resourcesService;

    private String searchQuery;

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    @PostConstruct
    public void init() {
        availableResources = resourcesService.getAvailableResources();
    }

    public List<ResourceDto> getAvailableResources() {
        if(searchQuery != null) {
            ResourceDto resourceDto = availableResources.stream()
                    .filter(x -> x.getId().toString().equals(searchQuery))
                    .findFirst()
                    .orElse(null);
            if (resourceDto == null) {
                return new ArrayList<>();
            }
            return Collections.singletonList(resourceDto);
        }
        return availableResources;
    }

    public void rent(ActionEvent event) throws RepositoryException, ObjectAlreadyStoredException, ResourceNotAvailableException, IOException {
        String login = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
        UUID value = (UUID) event.getComponent().getAttributes().get("selected");
        eventsService.rent(login, value);
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }

    public String search() {
        return "rentsPanel.xhtml?faces-redirect=true&searchQuery=" + searchQuery;
    }
}
