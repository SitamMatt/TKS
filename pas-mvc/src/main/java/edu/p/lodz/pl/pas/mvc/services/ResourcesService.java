package edu.p.lodz.pl.pas.mvc.services;

import edu.p.lodz.pl.pas.mvc.model.Book;
import edu.p.lodz.pl.pas.mvc.model.Event;
import edu.p.lodz.pl.pas.mvc.model.Magazine;
import edu.p.lodz.pl.pas.mvc.model.Resource;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectAlreadyStoredException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectLockedByRentException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectNotFoundException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.RepositoryException;
import edu.p.lodz.pl.pas.mvc.repositories.interfaces.IEventsRepository;
import edu.p.lodz.pl.pas.mvc.repositories.interfaces.IResourcesRepository;
import edu.p.lodz.pl.pas.mvc.services.dto.ResourceDto;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Named
@RequestScoped
public class ResourcesService {
    @Inject
    private IResourcesRepository resourcesRepository;
    @Inject
    private IEventsRepository eventsRepository;

    public void save(ResourceDto resource) throws ObjectAlreadyStoredException, RepositoryException, ObjectNotFoundException {
        if (resourcesRepository.has(resource.getId())) {
            resourcesRepository.update(mapBack(resource));
        } else {
            resourcesRepository.add(mapBack(resource));
        }
    }

    protected ResourceDto map(Resource resource) {
        String author = null, issueDate = null;
        if (resource instanceof Book) {
            author = ((Book) resource).getAuthor();
        } else if (resource instanceof Magazine) {
            issueDate = ((Magazine) resource).getIssueDate();
        }
        return new ResourceDto(
                resource.getId(),
                resource.getTitle(),
                resource.getPagesCount(),
                resource.getPublishingHouse(),
                issueDate,
                author,
                getResourceType(resource)
        );
    }

    protected Resource mapBack(ResourceDto dto){
        if(dto.getType().equals("Book")){
            return new Book(
                    dto.getId(),
                    dto.getTitle(),
                    dto.getPagesCount(),
                    dto.getPublishingHouse(),
                    dto.getAuthor()
            );
        }else if(dto.getType().equals("Magazine")){
            return new Magazine(
                    dto.getId(),
                    dto.getTitle(),
                    dto.getPagesCount(),
                    dto.getPublishingHouse(),
                    dto.getIssueDate()
            );
        }
        return null;
    }

    public ResourceDto find(UUID id) {
        Resource resource =  resourcesRepository.getById(id);
        return map(resource);
    }

    public boolean delete(UUID id) throws ObjectLockedByRentException, ObjectNotFoundException {
        if (isRented(id))
            throw new ObjectLockedByRentException();
        resourcesRepository.delete(id);
        return true;
    }

    public List<ResourceDto> getAllResources() {
        return resourcesRepository.getAll().stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public String getResourceType(Resource resource) {
        if (resource instanceof Book) {
            return "Book";
        } else if (resource instanceof Magazine) {
            return "Magazine";
        }
        return "";
    }

    public boolean isRented(UUID id) {
        return !eventsRepository.isAvailable(id);
    }

    public List<ResourceDto> getAvailableResources() {
        List<Event> rents = eventsRepository.getAllActiveRents();
        return resourcesRepository.getAll().stream()
                .filter(x -> rents.stream().noneMatch(e -> e.getResourceId().equals(x.getId())))
                .map(this::map)
                .collect(Collectors.toList());
    }
}
