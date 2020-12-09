package edu.p.lodz.pl.pas.mvc.services;

import edu.p.lodz.pl.pas.mvc.model.Event;
import edu.p.lodz.pl.pas.mvc.model.User;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectAlreadyStoredException;
import edu.p.lodz.pl.pas.mvc.repositories.EventsRepository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.NotFoundException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Named
@RequestScoped
public class EventsService {
    @Inject
    private EventsRepository eventsRepository;

    public void add(Event event) throws ObjectAlreadyStoredException {
        eventsRepository.add(event);
    }

    public Event get(UUID eventID){
        return eventsRepository.get(eventID);
    }

    public List<Event> getUserRents(User user){
        return eventsRepository.getAll().stream()
                .filter(event -> event.getRenter().equals(user))
                .collect(Collectors.toList());
    }

    public List<Event> getCurrentRents() {
        return eventsRepository.getAll().stream()
                .filter(event -> event.getReturnDate() == null)
                .collect(Collectors.toList());
    }

    public List<Event> getArchiveRents() {
        return eventsRepository.getAll().stream()
                .filter(event -> event.getReturnDate() != null)
                .collect(Collectors.toList());
    }

    public Event getEventByBook(UUID bookID){
        return getCurrentRents().stream()
                .filter(event -> event.getResource().getId().equals(bookID))
                .findAny()
                .orElse(null);
    }

    public void finishRent(UUID rentEventID){
        Event rentEvent = get(rentEventID);
        rentEvent.setReturnDate(new Date());
    }

}
