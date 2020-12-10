package edu.p.lodz.pl.pas.mvc.repositories.interfaces;

import edu.p.lodz.pl.pas.mvc.model.Resource;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectAlreadyStoredException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectNotFoundException;

import java.util.List;
import java.util.UUID;

public interface IResourcesRepository {
    void add(Resource resource) throws ObjectAlreadyStoredException;

    Resource get(UUID id);

    List<Resource> getAll();

    void update(UUID id, Resource resource) throws ObjectNotFoundException;

    boolean delete(UUID id);
}
