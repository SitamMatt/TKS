package repositories;


import mappers.Mapper;
import exceptions.ObjectAlreadyStoredException;
import exceptions.ObjectNotFoundException;
import exceptions.RepositoryException;
import model.Entity;
import repositories.interfaces.IRepositoryBase;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class RepositoryBase<T extends Entity> implements IRepositoryBase<T> {
    protected List<T> items;

    public synchronized boolean has(UUID id) {
        return items.stream().anyMatch(x -> x.getGuid().equals(id));
    }

    public synchronized T getByGuid(UUID id) {
        return items.stream().filter(x -> x.getGuid().equals(id)).findFirst().orElse(null);
    }

    @Override
    public synchronized int count(){
        return items.size();
    }

    @Override
    public synchronized List<T> getAll() {
        return items;
    }

    @Override
    public synchronized List<T> getPaged(int page, int maxResults){
        var result =  items.stream().skip((long) page * maxResults);
        if(maxResults != 0) result = result.limit(maxResults);
        return result.collect(Collectors.toList());
    }

    @Override
    public synchronized void add(T item) throws ObjectAlreadyStoredException, RepositoryException {
        if (has(item.getGuid())) throw new ObjectAlreadyStoredException();
        validate(item);
        item.setGuid(UUID.randomUUID());
        items.add(item);
    }

    @Override
    public synchronized void update(T item) throws ObjectNotFoundException, RepositoryException {
        T original = getByGuid(item.getGuid());
        if (original == null) throw new ObjectNotFoundException();
        validate(item);
        map(item, original);
    }

    protected abstract void map(T source, T destination) throws RepositoryException;

    protected void validate(T item) throws RepositoryException {
    }
}
