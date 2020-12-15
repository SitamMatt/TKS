package edu.p.lodz.pl.pas.mvc.controllers;

import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectAlreadyStoredException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.RepositoryException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ResourceNotAvailableException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.UserNotActiveException;
import edu.p.lodz.pl.pas.mvc.services.EventsService;
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
public class RentingController implements Serializable {
    private List<ResourceDto> availableResources;
    @Inject
    private EventsService eventsService;
    @Inject
    private ResourcesService resourcesService;
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("edu.p.lodz.pl.pas.mvc.messages");

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

    public void loadResources(){
        if(searchQuery != null) {
            ResourceDto resourceDto = availableResources.stream()
                    .filter(x -> x.getId().toString().equals(searchQuery))
                    .findFirst()
                    .orElse(null);
            if (resourceDto == null) {
                availableResources = new ArrayList<>();
            }else{
                availableResources = Collections.singletonList(resourceDto);
            }
        }
    }

    public List<ResourceDto> getAvailableResources() {
        return availableResources;
    }

    public void rent(ActionEvent event) throws IOException {
        try {
            String login = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
            UUID value = (UUID) event.getComponent().getAttributes().get("selected");
            eventsService.rent(login, value);
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        }
        catch (ObjectAlreadyStoredException e){
            e.printStackTrace();
            FacesContext context = FacesContext.getCurrentInstance();
            String res = resourceBundle.getString("object_stored");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, res, "");
            context.addMessage(null, message);
        }
        catch (ResourceNotAvailableException e){
            FacesContext context = FacesContext.getCurrentInstance();
            String res = resourceBundle.getString("res_not_available");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, res, "");
            context.addMessage(null, message);
        }
        catch (RepositoryException e){
            FacesContext context = FacesContext.getCurrentInstance();
            String res = resourceBundle.getString("rep_exception");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, res, "");
            context.addMessage(null, message);
        } catch (UserNotActiveException e) {
            e.printStackTrace();
            FacesContext context = FacesContext.getCurrentInstance();
            String res = resourceBundle.getString("user_inactive");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, res, "");
            context.addMessage(null, message);
        } finally {
            init();
        }
    }

    public String search() {
        return "rentsPanel.xhtml?faces-redirect=true&searchQuery=" + searchQuery;
    }
}
