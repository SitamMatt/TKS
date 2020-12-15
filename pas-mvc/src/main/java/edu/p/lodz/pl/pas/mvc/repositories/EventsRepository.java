package edu.p.lodz.pl.pas.mvc.repositories;

import edu.p.lodz.pl.pas.mvc.fillers.EventsFiller;
import edu.p.lodz.pl.pas.mvc.model.Event;
import edu.p.lodz.pl.pas.mvc.repositories.interfaces.IEventsRepository;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class EventsRepository extends RepositoryBase<Event> implements IEventsRepository {
    @Inject
    private EventsFiller eventsFiller;

    @PostConstruct
    public void eventsInit() {
        items = eventsFiller.fillEvents();
    }

    @Override
    protected void map(Event source, Event destination) {
        destination.map(source);
    }

    @Override
    public synchronized boolean isAvailable(UUID id) {
        return items.stream()
                .noneMatch(x -> x.getReturnDate() == null
                        && x.getResourceId().equals(id));
    }

    @Override
    public synchronized List<Event> getUserActiveRents(UUID id) {
        return items.stream()
                .filter(x -> x.getUserId().equals(id)
                        && x.getReturnDate() == null)
                .collect(Collectors.toList());
    }

    @Override
    public synchronized Event getActiveForUserAndResource(UUID userId, UUID resId) {
        return items.stream()
                .filter(x -> x.getUserId().equals(userId)
                        && x.getResourceId().equals(resId)
                        && x.getReturnDate() == null)
                .findFirst()
                .orElse(null);
    }

    @Override
    public synchronized List<Event> getAllActiveRents() {
        return items.stream()
                .filter(x -> x.getReturnDate() == null)
                .collect(Collectors.toList());
    }

    @Override
    public synchronized List<Event> getAllArchiveRents() {
        return items.stream()
                .filter(x -> x.getReturnDate() != null)
                .collect(Collectors.toList());
    }


}
