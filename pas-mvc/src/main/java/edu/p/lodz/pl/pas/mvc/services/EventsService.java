package edu.p.lodz.pl.pas.mvc.services;

import edu.p.lodz.pl.pas.mvc.model.Event;
import edu.p.lodz.pl.pas.mvc.model.User;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectAlreadyStoredException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.RepositoryException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ResourceNotAvailableException;
import edu.p.lodz.pl.pas.mvc.repositories.interfaces.IEventsRepository;
import edu.p.lodz.pl.pas.mvc.repositories.interfaces.IResourcesRepository;
import edu.p.lodz.pl.pas.mvc.repositories.interfaces.IUsersRepository;
import edu.p.lodz.pl.pas.mvc.services.dto.EventDto;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Named
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

    public void rent(String login, UUID resId) throws ResourceNotAvailableException, ObjectAlreadyStoredException, RepositoryException {
        if(!eventsRepository.isAvailable(resId)) throw new ResourceNotAvailableException();
        User user = usersRepository.findUserByLogin(login);
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

//    public List<Event> getCurrentUserRents(User user) {
//        return null;
//        return getCurrentRents().stream()
//                .filter(event -> event.getRenter().getId().equals(user.getId()))
//                .collect(Collectors.toList());
//    }

//    public List<Event> getCurrentRents() {
//        return null;
//        return eventsRepository.getAll().stream()
//                .filter(event -> event.getReturnDate() == null)
//                .collect(Collectors.toList());
//    }

//    public List<Event> getArchiveRents() {
//        return null;
//        return eventsRepository.getAll().stream()
//                .filter(event -> event.getReturnDate() != null)
//                .collect(Collectors.toList());
//    }

//    public Event getEventByBook(UUID bookID) {
//        return null;
//        return getCurrentRents().stream()
//                .filter(event -> event.getResource().getId().equals(bookID))
//                .findAny()
//                .orElse(null);
//    }
//
//    public void finishRent(UUID rentEventID) {
//        Event rentEvent = find(rentEventID);
//        rentEvent.setReturnDate(new Date());
//    }

}
