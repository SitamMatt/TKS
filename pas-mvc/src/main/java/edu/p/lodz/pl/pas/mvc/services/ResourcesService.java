package edu.p.lodz.pl.pas.mvc.services;

import edu.p.lodz.pl.pas.mvc.model.Book;
import edu.p.lodz.pl.pas.mvc.model.Magazine;
import edu.p.lodz.pl.pas.mvc.model.Resource;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectAlreadyStoredException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectNotFoundException;
import edu.p.lodz.pl.pas.mvc.repositories.IResourcesRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.UUID;

@Named
@RequestScoped
public class ResourcesService {
    @Inject
    private IResourcesRepository IResourcesRepository;

    public void add(Resource resource) {
        try {
            IResourcesRepository.add(resource);
        } catch (ObjectAlreadyStoredException ignored) { }
    }

    public Resource get(UUID id) {
        return IResourcesRepository.get(id);
    }

    public void update(UUID id, Resource resource) {
        try {
            IResourcesRepository.update(id, resource);
        } catch (ObjectNotFoundException ignored) { }
    }

    public boolean delete(UUID id) {
        return IResourcesRepository.delete(id);
    }

    public List<Resource> getAllResources() {
        return IResourcesRepository.getAll();
    }

    public void save(Resource resource) {
        try {
            if(IResourcesRepository.getAll().stream().anyMatch(x -> x.getId().equals(resource.getId()))){
                IResourcesRepository.update(resource.getId(),resource);
            }else{
                IResourcesRepository.add(resource);
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
}
