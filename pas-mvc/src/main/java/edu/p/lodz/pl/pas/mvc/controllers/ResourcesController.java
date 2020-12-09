package edu.p.lodz.pl.pas.mvc.controllers;

import edu.p.lodz.pl.pas.mvc.controllers.dto.ResourceDTO;
import edu.p.lodz.pl.pas.mvc.repositories.ResourcesRepository;
import edu.p.lodz.pl.pas.mvc.services.ResourcesService;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequestScoped
@Named
@RolesAllowed({"ADMIN", "WORKER", "CLIENT"})
@Stateful
public class ResourcesController {
    @Inject
    private ResourcesService resourcesService;

    public List<ResourceDTO> getAllResources() {
        List<ResourceDTO> ress = new ArrayList<>();
        resourcesService.getAllResources()
                .forEach(resource -> ress.add(ResourcesRepository.toDTO(resource)));
        return ress;
    }

    public String editResource() {
        return "ResourceEdit";
    }

    public String createResource() {
        return "ResourceCreate";
    }

    public void removeResource(String id) throws IOException {
        resourcesService.delete(UUID.fromString(id));
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }
}
