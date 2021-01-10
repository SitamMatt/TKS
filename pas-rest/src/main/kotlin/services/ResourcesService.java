package services;

import model.Book;
import model.Event;
import model.Magazine;
import model.Resource;
import model.exceptions.ObjectAlreadyStoredException;
import model.exceptions.ObjectLockedByRentException;
import model.exceptions.ObjectNotFoundException;
import model.exceptions.RepositoryException;
import repositories.interfaces.IEventsRepository;
import repositories.interfaces.IResourcesRepository;
import services.dto.ResourceDto;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
                resource.getGuid(),
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
        Resource resource =  resourcesRepository.getByGuid(id);
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
                .filter(x -> rents.stream().noneMatch(e -> e.getResourceId().equals(x.getGuid())))
                .map(this::map)
                .collect(Collectors.toList());
    }
}
