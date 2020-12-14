package edu.p.lodz.pl.pas.mvc.repositories;

import edu.p.lodz.pl.pas.mvc.model.Entity;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectAlreadyStoredException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectNotFoundException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.RepositoryException;
import edu.p.lodz.pl.pas.mvc.repositories.interfaces.IRepositoryBase;

import java.util.List;
import java.util.UUID;

public abstract class RepositoryBase<T extends Entity> implements IRepositoryBase<T> {
    protected List<T> items;

    public synchronized boolean has(UUID id) {
        return items.stream().anyMatch(x -> x.getId() == id);
    }

    public synchronized T getById(UUID id) {
        return items.stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public synchronized List<T> getAll() {
        return items;
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
