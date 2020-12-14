package edu.p.lodz.pl.pas.mvc.repositories.interfaces;

import edu.p.lodz.pl.pas.mvc.model.Event;
import edu.p.lodz.pl.pas.mvc.services.dto.EventDto;

import java.util.List;
import java.util.UUID;

public interface IEventsRepository extends IRepositoryBase<Event>{
    boolean isAvailable(UUID id);
    List<Event> getUserActiveRents(UUID id);
    Event getActiveForUserAndResource(UUID userId, UUID resId);

    List<Event> getAllActiveRents();

    List<Event> getAllArchiveRents();
}
