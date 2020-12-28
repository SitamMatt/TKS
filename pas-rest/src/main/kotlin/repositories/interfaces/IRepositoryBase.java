package repositories.interfaces;


import model.Entity;
import model.exceptions.ObjectAlreadyStoredException;
import model.exceptions.ObjectNotFoundException;
import model.exceptions.RepositoryException;

import java.util.List;
import java.util.UUID;

public interface IRepositoryBase<T extends Entity> {
    boolean has(UUID id);
    T getById(UUID id);
    List<T> getAll();
    void add(T item) throws ObjectAlreadyStoredException, RepositoryException;
    void update(T item) throws ObjectNotFoundException, RepositoryException;
}
