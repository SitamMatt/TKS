package edu.p.lodz.pl.pas.mvc.repositories;

import edu.p.lodz.pl.pas.mvc.model.Event;
import edu.p.lodz.pl.pas.mvc.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public List<Event> getUserRents(User user){
        List<Event> userRents = new ArrayList<>();
        for (Event i : items){
            if (i.getRenter().equals(user))
                userRents.add(i);
        }
        return userRents;
    }
}
