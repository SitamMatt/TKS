package services;

import dto.ResourceBaseDto;
import dto.ResourceGetDto;
import dto.ResourceType;
import exceptions.*;
import mappers.Mapper;
import mappers.MapperHelper;
import mappers.Mapperrek;
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
    @Inject private Mapper mapper;
    @Inject private MapperHelper helper;
    @Inject private Mapperrek m;

    public void add(ResourceBaseDto model) throws ObjectAlreadyStoredException, RepositoryException {
        var resource = (Resource) mapper.getMapper().map(model,
                getType(Objects.requireNonNull(model.getType())));
        resourcesRepository.add(resource);
    }

    public void update(UUID guid, ResourceBaseDto model) throws RepositoryException, ObjectNotFoundException, ObjectLockedByRentException {
        if (!eventsRepository.isAvailable(guid))
            throw new ObjectLockedByRentException();
        var resource = (Resource) mapper.getMapper().map(model,
                getType(Objects.requireNonNull(model.getType())));
        resourcesRepository.update(resource);
    }

    protected Class<?> getType(ResourceType type) {
        switch (type){
            case Book:
                return Book.class;
            case Magazine:
                return Magazine.class;
            default:
                return null;
//                throw new Exception(); //todo create new type
        }
    }

    public ResourceGetDto find(UUID id) {
        Resource resource =  resourcesRepository.getByGuid(id);
        return mapper.getMapper().map(resource, ResourceGetDto.class);
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
                .map(r -> mapper.getMapper().map(r, ResourceGetDto.class))
//                .map(helper.getMapper()::mapResourceToDto)
//                .map(x -> m.mapToDto(x))
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
        return stream.map(x -> mapper.getMapper().map(x, ResourceGetDto.class))
                .collect(Collectors.toList());
    }

    //todo checks
    public List<ResourceGetDto> getUserResources(String login) throws UserNotFoundException {
        var user = usersRepository.findUserByLogin(login);
        if(user == null) throw new UserNotFoundException();
        var rents = eventsRepository.getUserActiveRents(user.getGuid());
        return rents.stream()
                .map(r -> resourcesRepository.getByGuid(r.getResourceId()))
                .map(r -> mapper.getMapper().map(r, ResourceGetDto.class))
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

    public void returnResource(String login, UUID resource) throws Exception {
        var user = usersRepository.findUserByLogin(login);
//        if(eventsRepository.isAvailable(resource)) throw new Exception();
        var event = eventsRepository
                .getActiveForUserAndResource(user.getGuid(), resource);
        if(event == null) throw new ResourceReturnException();
        event.setReturnDate(new Date()); // todo consider calling update method
    }
}
