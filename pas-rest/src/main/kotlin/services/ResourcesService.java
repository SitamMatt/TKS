package services;

import dto.ResourceBaseDto;
import dto.ResourceGetDto;
import exceptions.*;
import mappers.MapperHelper;
import model.Book;
import model.Event;
import model.Magazine;
import model.Resource;
import repositories.interfaces.IEventsRepository;
import repositories.interfaces.IResourcesRepository;
import repositories.interfaces.IUsersRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestScoped
public class ResourcesService {
    @Inject private IResourcesRepository resourcesRepository;
    @Inject private IEventsRepository eventsRepository;
    @Inject private IUsersRepository usersRepository;
    @Inject private MapperHelper helper;

    public void add(ResourceBaseDto model) throws ObjectAlreadyStoredException, RepositoryException {
        var resource = (Resource) helper.getMapper().mapDtoToResource(model);
        resourcesRepository.add(resource);
    }

    //todo: resource nie ma przypisanego guida po mapowaniu
    public void update(UUID guid, ResourceBaseDto model) throws ObjectLockedByRentException, RepositoryException, ObjectNotFoundException {
        if (!eventsRepository.isAvailable(guid))
            throw new ObjectLockedByRentException();
        var resource = (Resource) helper.getMapper().mapDtoToResource(model);
        resource.setGuid(guid);
        resourcesRepository.update(resource);
    }

    public ResourceGetDto find(UUID id) throws ObjectNotFoundException {
        Resource resource =  resourcesRepository.getByGuid(id);
        if (resource == null) throw new ObjectNotFoundException();
        var mapper = helper.getMapper();
        return mapper.mapResourceToDto(resource);
    }

    public boolean delete(UUID id) throws ObjectLockedByRentException, ObjectNotFoundException {
        if (!eventsRepository.isAvailable(id))
            throw new ObjectLockedByRentException();
        resourcesRepository.delete(id);
        return true;
    }

    public String getResourceType(Resource resource) {
        if (resource instanceof Book) {
            return "Book";
        } else if (resource instanceof Magazine) {
            return "Magazine";
        }
        return "";
    }

    public List<ResourceGetDto> getAvailableResources() {
        var rents = eventsRepository.getActiveRents();
        return resourcesRepository.getAll().stream()
                .filter(x -> rents.stream().noneMatch(e -> Objects
                                        .equals(e.getResourceId(), x.getGuid())))
                .map(helper.getMapper()::mapResourceToDto)
                .collect(Collectors.toList());
    }

    public List<ResourceGetDto> filter(String type, int page, int maxResults, String search) {
        if(page != 0 && maxResults == 0) maxResults = resourcesRepository.count() / page;
        var stream = resourcesRepository.getPaged(page, maxResults).stream();
        if(type != null) switch(type){
            case "BOOK":
                stream = stream.filter(x -> x instanceof Book);
                break;
            case "MAGAZINE":
                stream = stream.filter(x -> x instanceof Magazine);
                break;
        }
        if(search != null){
            stream = stream.filter(x -> {
                var result = x.getGuid().toString().contains(search);
                result |= x.getTitle().contains(search);
                result |= x.getPublishingHouse().contains(search);
                if(x instanceof Book)
                    result |= ((Book)x).getAuthor().contains(search);
                else if(x instanceof Magazine)
                    result |= ((Magazine)x).getIssueDate().contains(search);
                return result;
            });
        }
        return stream
                .map(helper.getMapper()::mapResourceToDto)
                .collect(Collectors.toList());
    }

    //todo checks
    public List<ResourceGetDto> getUserResources(String login) throws UserNotFoundException {
        var user = usersRepository.findUserByLogin(login);
        if(user == null) throw new UserNotFoundException();
        var rents = eventsRepository.getUserActiveRents(user.getGuid());
        return rents.stream()
                .map(r -> resourcesRepository.getByGuid(r.getResourceId()))
                .map(helper.getMapper()::mapResourceToDto)
                .collect(Collectors.toList());
    }

    public void rent(String login, UUID resource) throws ResourceNotAvailableException, ObjectAlreadyStoredException, RepositoryException {
        var user = usersRepository.findUserByLogin(login);
        if(!eventsRepository.isAvailable(resource)) throw new ResourceNotAvailableException();
        var event = new Event();
        event.setUserId(user.getGuid());
        event.setRentDate(new Date());
        event.setResourceId(resource);
        eventsRepository.add(event);
    }

    //todo checks
    public void returnResource(String login, UUID resource) throws ObjectNotFoundException, ResourceReturnException, ResourceNotAvailableException {
        if(eventsRepository.isAvailable(resource)) throw new ResourceNotAvailableException();
        var user = usersRepository.findUserByLogin(login);
        if (user == null)
            throw new ObjectNotFoundException();
        var event = eventsRepository
                .getActiveForUserAndResource(user.getGuid(), resource);
        if(event == null) throw new ResourceReturnException();
        event.setReturnDate(new Date());
    }
}
