package services;


import model.Event;
import model.User;
import model.exceptions.ObjectAlreadyStoredException;
import model.exceptions.RepositoryException;
import model.exceptions.ResourceNotAvailableException;
import model.exceptions.UserNotActiveException;
import repositories.interfaces.IEventsRepository;
import repositories.interfaces.IResourcesRepository;
import repositories.interfaces.IUsersRepository;
import services.dto.EventDto;
import utils.UtilsKt;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestScoped
public class EventsService {
    @Inject
    private IEventsRepository eventsRepository;
    @Inject
    private IUsersRepository usersRepository;
    @Inject
    private IResourcesRepository resourcesRepository;

    public void add(EventDto event) throws ObjectAlreadyStoredException, RepositoryException {
        eventsRepository.add(mapBack(event));
    }

    protected Event mapBack(EventDto dto) {
        return new Event(
                dto.getId(),
                dto.getRentDate(),
                dto.getReturnDate(),
                dto.getResource().getGuid(),
                dto.getRenter().getGuid()
        );
    }

    protected EventDto map(Event event) {
//        return new EventDto(
//                event.getGuid(),
//                event.getRentDate(),
//                event.getReturnDate(),
//                usersRepository.getByGuid(event.getUserId()),
//                resourcesRepository.getByGuid(event.getResourceId())
//        );
        return null;
    }

    public Event find(UUID id) {
        return eventsRepository.getByGuid(id);
    }

    public List<EventDto> getUserActiveRents(UUID id) {
        return eventsRepository
                .getUserActiveRents(id)
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public void returnResource(UUID userId, UUID resId) throws Exception {
        Event event = eventsRepository.getActiveForUserAndResource(userId, resId);
        if (event == null) throw new Exception();
        event.setReturnDate(new Date());
        eventsRepository.update(event);
    }

    public void rent(String login, UUID resId) throws ResourceNotAvailableException, ObjectAlreadyStoredException, RepositoryException, UserNotActiveException {
//        if (!eventsRepository.isAvailable(resId)) throw new ResourceNotAvailableException();
//        User user = usersRepository.findUserByLogin(login);
//        if (!user.isActive()) throw new UserNotActiveException();
//        Event event = new Event(
//                null,
//                new Date(),
//                null,
//                user.getGuid(),
//                resId
//        );
//        eventsRepository.add(event);
    }

    public List<EventDto> getAllActiveRents(Integer page, Integer maxResults) {
        if(page != null && maxResults == null) maxResults = eventsRepository.count();
        return eventsRepository.getPaged(UtilsKt.coalesce(page, 0), maxResults).stream().map(this::map).collect(Collectors.toList());
    }

    public List<EventDto> getAllActiveRents() {
        List<EventDto> collect = eventsRepository.getAllActiveRents().stream()
                .map(this::map)
                .collect(Collectors.toList());
        return collect;
    }

    public List<EventDto> getAllArchiveRents() {
        List<EventDto> collect = eventsRepository.getAllArchiveRents().stream()
                .map(this::map)
                .collect(Collectors.toList());
        return collect;
    }

    public List<Event> filter(String type, int page, int maxResults, String search) {
        if(page != 0 && maxResults == 0) maxResults = eventsRepository.count() / page;
        var stream = eventsRepository.getPaged(page, maxResults).stream();
        if(type != null) switch(type){
            case "active":
                stream = stream.filter(x -> x.getReturnDate() == null);
                break;
            case "archive":
                stream = stream.filter(x -> x.getReturnDate() != null);
                break;
        }
        if(search != null){
            stream = stream.filter(x -> x.getGuid().toString().contains(search));
        }
        return stream.collect(Collectors.toList());
    }
}
