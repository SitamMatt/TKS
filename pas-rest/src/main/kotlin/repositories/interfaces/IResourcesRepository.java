package repositories.interfaces;


import model.Resource;
import model.exceptions.ObjectNotFoundException;

import java.util.UUID;

public interface IResourcesRepository extends IRepositoryBase<Resource> {
    boolean delete(UUID id) throws ObjectNotFoundException;
}
