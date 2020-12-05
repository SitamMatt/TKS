package edu.p.lodz.pl.pas.mvc.controllers;

import edu.p.lodz.pl.pas.mvc.controllers.dto.ResourceDTO;
import edu.p.lodz.pl.pas.mvc.model.Book;
import edu.p.lodz.pl.pas.mvc.model.Magazine;
import edu.p.lodz.pl.pas.mvc.model.Resource;
import edu.p.lodz.pl.pas.mvc.model.User;
import edu.p.lodz.pl.pas.mvc.repositories.ResourcesRepository;
import edu.p.lodz.pl.pas.mvc.services.ResourcesService;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

//    public String getResourceType(Resource resource) {
//        return resourcesService.getResourceType(resource);
//    }

}
