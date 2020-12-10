package edu.p.lodz.pl.pas.mvc.repositories;

import edu.p.lodz.pl.pas.mvc.model.Event;

import java.util.List;
import java.util.UUID;

public interface IEventsRepository {
    void add(Event event);

    Event get(UUID id);

    List<Event> getAll();

    boolean isAvailable(UUID id);
}
