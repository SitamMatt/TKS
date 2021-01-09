package repositories;


import model.Entity;
import model.exceptions.ObjectAlreadyStoredException;
import model.exceptions.ObjectNotFoundException;
import model.exceptions.RepositoryException;
import repositories.interfaces.IRepositoryBase;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class RepositoryBase<T extends Entity> implements IRepositoryBase<T> {
    protected List<T> items;

    public synchronized boolean has(UUID id) {
        return items.stream().anyMatch(x -> x.getId().equals(id));
    }

    public synchronized T getById(UUID id) {
        return items.stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
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
        if (has(item.getId())) throw new ObjectAlreadyStoredException();
        validate(item);
        item.setId(UUID.randomUUID());
        items.add(item);
    }

    @Override
    public synchronized void update(T item) throws ObjectNotFoundException, RepositoryException {
        T original = getById(item.getId());
        if (original == null) throw new ObjectNotFoundException();
        validate(item);
        map(item, original);
    }

    protected abstract void map(T source, T destination) throws RepositoryException;

    protected void validate(T item) throws RepositoryException {
    }
}
