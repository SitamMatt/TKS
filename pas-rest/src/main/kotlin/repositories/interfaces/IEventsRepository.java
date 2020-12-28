package repositories.interfaces;


import model.Event;

import java.util.List;
import java.util.UUID;

public interface IEventsRepository extends IRepositoryBase<Event>{
    boolean isAvailable(UUID id);
    List<Event> getUserActiveRents(UUID id);
    Event getActiveForUserAndResource(UUID userId, UUID resId);

    List<Event> getAllActiveRents();

    List<Event> getAllArchiveRents();
}
