package repositories.interfaces;


import exceptions.ObjectAlreadyStoredException;
import exceptions.ObjectNotFoundException;
import exceptions.RepositoryException;
import model.Entity;

import java.util.List;
import java.util.UUID;

public interface IRepositoryBase<T extends Entity> {
    boolean has(UUID id);
    T getByGuid(UUID id);
    int count();
    List<T> getAll();
    List<T> getPaged(int page, int maxResults);
    void add(T item) throws ObjectAlreadyStoredException, RepositoryException;
    void update(T item) throws ObjectNotFoundException, RepositoryException;
}
