package repositories;

import exceptions.RepositoryException;
import fillers.NewResourcesFiller;
import mappers.Mapper;
import exceptions.ObjectNotFoundException;
import mappers.MapperHelper;
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
    private MapperHelper mapperHelper;

    @PostConstruct
    public void resourcesInit() {
        items = resourcesFiller.fill();
    }

    @Override
    protected synchronized void map(Resource source, Resource destination) {
        mapperHelper.getEntityMapper().fromResource(source, destination);
    }

    @Override
    public synchronized boolean delete(UUID id) throws ObjectNotFoundException {
        Resource item = getByGuid(id);
        if (item == null) throw new ObjectNotFoundException();
        return items.remove(item);
    }
}
