package edu.p.lodz.pl.pas.mvc.repositories;

import edu.p.lodz.pl.pas.mvc.RefUtils;
import edu.p.lodz.pl.pas.mvc.model.Event;

import java.util.List;
import java.util.UUID;

public class EventsRepository {
    private List<Event> items;

    public void add(Event event){
        if(event.getId() == null) {
            assignId(event);
        }
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

    private void assignId(Event event) {
        try {
            RefUtils.setFieldValue(event, "id", UUID.randomUUID());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public boolean isAvailable(UUID id) {
        return items.stream().noneMatch(x -> x.getResource().getId() == id && x.getReturnDate() == null);
    }
}
