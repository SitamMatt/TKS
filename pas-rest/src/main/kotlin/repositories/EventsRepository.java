package repositories;

import exceptions.RepositoryException;
import fillers.NewEventsFiller;
import mappers.Mapper;
import mappers.MapperHelper;
import model.Event;
import repositories.interfaces.IEventsRepository;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class EventsRepository extends RepositoryBase<Event> implements IEventsRepository {
    @Inject
    private NewEventsFiller eventsFiller;
    @Inject
    private MapperHelper mapperHelper;

    @PostConstruct
    public void eventsInit() {
        items = eventsFiller.fill();
    }

    @Override
    public synchronized boolean isAvailable(UUID id) {
        return items.stream()
                .noneMatch(x -> x.getReturnDate() == null
                        && Objects.equals(x.getResourceId(), id));
    }

    @Override
    public synchronized List<Event> getUserActiveRents(UUID guid) {
        return items.stream()
                .filter(x -> Objects.equals(x.getUserId(), guid)
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

    public synchronized List<Event> getActiveRents() {
        return items.stream()
                .filter(x -> x.getReturnDate() == null)
                .collect(Collectors.toList());
    }

    @Override
    protected void map(Event source, Event destination) throws RepositoryException {
        mapperHelper.getEntityMapper().map(source, destination);
    }
}
