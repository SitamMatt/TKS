package repositories;

import fillers.NewResourcesFiller;
import mappers.Mapper;
import exceptions.ObjectNotFoundException;
import model.Resource;
import repositories.interfaces.IResourcesRepository;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.UUID;


@ApplicationScoped
public class ResourcesRepository extends RepositoryBase<Resource> implements IResourcesRepository {
    @Inject
    private NewResourcesFiller resourcesFiller;
    @Inject
    private Mapper mapper;

    @PostConstruct
    public void resourcesInit() {
        items = resourcesFiller.fill();
    }

    @Override
    public synchronized boolean delete(UUID id) throws ObjectNotFoundException {
        Resource item = getByGuid(id);
        if (item == null) throw new ObjectNotFoundException();
        return items.remove(item);
    }
}
