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
                dto.getResource().getId(),
                dto.getRenter().getId()
        );
    }

    protected EventDto map(Event event){
        return new EventDto(
                event.getId(),
                event.getRentDate(),
                event.getReturnDate(),
                usersRepository.getById(event.getUserId()),
                resourcesRepository.getById(event.getResourceId())
        );
    }

    public Event find(UUID id) {
        return eventsRepository.getById(id);
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
        if(event == null) throw new Exception();
        event.setReturnDate(new Date());
        eventsRepository.update(event);
    }

    public void rent(String login, UUID resId) throws ResourceNotAvailableException, ObjectAlreadyStoredException, RepositoryException, UserNotActiveException {
        if(!eventsRepository.isAvailable(resId)) throw new ResourceNotAvailableException();
        User user = usersRepository.findUserByLogin(login);
        if(!user.isActive()) throw new UserNotActiveException();
        Event event = new Event(
                null,
                new Date(),
                null,
                user.getId(),
                resId
        );
        eventsRepository.add(event);
    }

    public List<EventDto> getAllActiveRents(){
        List<EventDto> collect = eventsRepository.getAllActiveRents().stream()
                .map(this::map)
                .collect(Collectors.toList());
        return collect;
    }

    public List<EventDto> getAllArchiveRents(){
        List<EventDto> collect = eventsRepository.getAllArchiveRents().stream()
                .map(this::map)
                .collect(Collectors.toList());
        return collect;
    }
}
