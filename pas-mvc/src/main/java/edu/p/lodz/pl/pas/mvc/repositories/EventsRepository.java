package edu.p.lodz.pl.pas.mvc.repositories;

import edu.p.lodz.pl.pas.mvc.model.Event;

import java.util.List;

public class EventsRepository {
    private List<Event> items;

    public void add(Event event){
        items.add(event);
    }

    public Event get(UUID id){
        return items.stream()
            .filter(event -> event.getId() == id)
            .findAny()
            .orElse(null);
    }
}
