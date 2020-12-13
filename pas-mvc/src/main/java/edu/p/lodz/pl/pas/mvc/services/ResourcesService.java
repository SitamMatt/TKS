package edu.p.lodz.pl.pas.mvc.services;

import edu.p.lodz.pl.pas.mvc.model.Book;
import edu.p.lodz.pl.pas.mvc.model.Magazine;
import edu.p.lodz.pl.pas.mvc.model.Resource;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectAlreadyStoredException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectNotFoundException;
import edu.p.lodz.pl.pas.mvc.repositories.EventsRepository;
import edu.p.lodz.pl.pas.mvc.repositories.interfaces.IResourcesRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.UUID;

@Named
@RequestScoped
public class ResourcesService {
    @Inject
    private IResourcesRepository resourcesRepository;
    @Inject
    private EventsService eventsService;

    public void add(Resource resource) {
        try {
            resourcesRepository.add(resource);
        } catch (ObjectAlreadyStoredException ignored) { }
    }

    public Resource get(UUID id) {
        return resourcesRepository.get(id);
    }

    public void update(UUID id, Resource resource) {
        try {
            resourcesRepository.update(id, resource);
        } catch (ObjectNotFoundException ignored) { }
    }

    public boolean delete(UUID id) {
        if (isRented(id))
            return resourcesRepository.delete(id);
        else return false;
    }

    public List<Resource> getAllResources() {
        return resourcesRepository.getAll();
    }

    public void save(Resource resource) {
        try {
            if(resourcesRepository.getAll().stream().anyMatch(x -> x.getId().equals(resource.getId()))){
                resourcesRepository.update(resource.getId(),resource);
            }else{
                resourcesRepository.add(resource);
            }
        } catch(Exception ignored) {}
    }

    public String getResourceType(Resource resource) {
        if(resource instanceof Book) {
            return "Book";
        } else if(resource instanceof Magazine) {
            return "Magazine";
        }
        return "";
    }

    public boolean isRented(UUID id){
        return eventsService.getCurrentRents().stream()
                .filter(res -> res.getResource().getId().equals(id))
                .findAny()
                .orElse(null) == null;
    }
}
