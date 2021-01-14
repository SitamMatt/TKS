package repositories.interfaces;


import exceptions.ObjectNotFoundException;
import model.Resource;

import java.util.UUID;

public interface IResourcesRepository extends IRepositoryBase<Resource> {
    boolean delete(UUID id) throws ObjectNotFoundException;
}
