package edu.p.lodz.pl.pas.mvc.repositories;

import edu.p.lodz.pl.pas.mvc.model.Event;
import edu.p.lodz.pl.pas.mvc.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class EventsRepository {
    private List<Event> items;

    public void add(Event event){
        items.add(event);
    }

    public Event get(UUID id){
        return items.stream()
            .filter(event -> event.getId().equals(id))
            .findAny()
            .orElse(null);
    }

    public List<Event> getAll(){
        return items;
    }


}
