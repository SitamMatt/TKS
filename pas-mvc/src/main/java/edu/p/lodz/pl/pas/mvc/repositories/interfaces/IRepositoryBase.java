package edu.p.lodz.pl.pas.mvc.repositories.interfaces;

import edu.p.lodz.pl.pas.mvc.model.Entity;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectAlreadyStoredException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectNotFoundException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.RepositoryException;

import java.util.List;
import java.util.UUID;

public interface IRepositoryBase<T extends Entity> {
    boolean has(UUID id);
    T getById(UUID id);
    List<T> getAll();
    void add(T item) throws ObjectAlreadyStoredException, RepositoryException;
    void update(T item) throws ObjectNotFoundException, RepositoryException;
}