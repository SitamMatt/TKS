package edu.p.lodz.pl.pas.mvc.repositories;

import edu.p.lodz.pl.pas.mvc.RefUtils;
import edu.p.lodz.pl.pas.mvc.fillers.EventsFiller;
import edu.p.lodz.pl.pas.mvc.model.Event;
import edu.p.lodz.pl.pas.mvc.repositories.interfaces.IEventsRepository;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class EventsRepository implements IEventsRepository {
    private List<Event> items;
    @Inject
    private EventsFiller eventsFiller;

    @PostConstruct
    public void eventsInit() {
        List<Event> list = eventsFiller.fillEvents();
        items = new ArrayList<>(list.size());
        list.forEach(this::add);
    }

    @Override
    public synchronized void add(Event event){
        if(event.getId() == null) {
            assignId(event);
        }
        items.add(event);
    }

    @Override
    public synchronized Event get(UUID id){
        return items.stream()
            .filter(event -> event.getId().equals(id))
            .findAny()
            .orElse(null);
    }

    @Override
    public synchronized List<Event> getAll(){
        return items;
    }

    private synchronized void assignId(Event event) {
        try {
            RefUtils.setFieldValue(event, "id", UUID.randomUUID());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized boolean isAvailable(UUID id) {
        return items.stream()
                .noneMatch(x -> x.getResource().getId().equals(id)
                        && x.getReturnDate() == null
                        && x.getReturnDate().after(new Date()));
    }
}
