package edu.p.lodz.pl.pas.mvc.controllers;

import edu.p.lodz.pl.pas.mvc.controllers.dto.ResourceDTO;
import edu.p.lodz.pl.pas.mvc.services.ResourcesService;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;

import static edu.p.lodz.pl.pas.mvc.repositories.ResourcesRepository.fromDTO;
import static edu.p.lodz.pl.pas.mvc.repositories.ResourcesRepository.toDTO;

@ViewScoped
@Named
@RolesAllowed({"ADMIN", "WORKER"})
@Stateful
public class ResourceEditController {
    @Inject
    private ResourcesService resourcesService;

    private String resUUID;
    private ResourceDTO newResource;

    @PostConstruct
    public void init() {
        if (resUUID == null) {
            newResource = new ResourceDTO();
        } else {
            newResource = toDTO(resourcesService.get(UUID.fromString(resUUID)));
            resUUID = null;
        }
    }

    public void saveResource() {
        resourcesService.save(fromDTO(newResource));
        newResource = new ResourceDTO();
        resUUID = null;
    }

    public String getResUUID() {
        return resUUID;
    }

    public void setResUUID(String resUUID) {
        this.resUUID = resUUID;
    }

    public ResourceDTO getNewResource() {
        return newResource;
    }

    public void setNewResource(ResourceDTO newResource) {
        this.newResource = newResource;
    }
}