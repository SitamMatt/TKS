package edu.p.lodz.pl.pas.mvc.controllers;

import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectLockedByRentException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectNotFoundException;
import edu.p.lodz.pl.pas.mvc.services.ResourcesService;
import edu.p.lodz.pl.pas.mvc.services.dto.ResourceDto;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequestScoped
@Named
public class ResourcesController {
    @Inject
    private ResourcesService resourcesService;

    public List<ResourceDto> listResources() {
        return resourcesService.getAllResources();
    }

    public String editResource() {
        return "ResourceEdit";
    }

    public String createResource() {
        return "ResourceCreate";
    }

    public void removeResource(String id) throws IOException, ObjectLockedByRentException, ObjectNotFoundException {
        resourcesService.delete(UUID.fromString(id));
//        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }
}
