package edu.p.lodz.pl.pas.mvc.controllers;

import edu.p.lodz.pl.pas.mvc.model.Resource;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectAlreadyStoredException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.RepositoryException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ResourceNotAvailableException;
import edu.p.lodz.pl.pas.mvc.services.EventsService;
import edu.p.lodz.pl.pas.mvc.services.RentsService;
import edu.p.lodz.pl.pas.mvc.services.ResourcesService;
import edu.p.lodz.pl.pas.mvc.services.UsersService;
import edu.p.lodz.pl.pas.mvc.services.dto.ResourceDto;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.UUID;

@RequestScoped
@Named
public class RentingController {
    @Inject
    private EventsService eventsService;
    @Inject
    private ResourcesService resourcesService;
    @Inject
    private UsersService usersService;

    public List<ResourceDto> getAvailableResources(){
        return resourcesService.getAvailableResources();
    }

    public void rent(UUID resId) throws RepositoryException, ObjectAlreadyStoredException, ResourceNotAvailableException {
        String login = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
        eventsService.rent(login, resId);
    }
}
