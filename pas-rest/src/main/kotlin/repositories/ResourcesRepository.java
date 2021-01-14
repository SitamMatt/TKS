package repositories;

import fillers.ResourcesFiller;
import model.Resource;
import model.exceptions.ObjectNotFoundException;
import model.exceptions.RepositoryException;
import repositories.interfaces.IResourcesRepository;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.UUID;


@ApplicationScoped
public class ResourcesRepository extends RepositoryBase<Resource> implements IResourcesRepository {
    @Inject
    private ResourcesFiller resourcesFiller;

    @PostConstruct
    public void resourcesInit() {
        items = resourcesFiller.fillResources();
    }

    @Override
    protected void map(Resource source, Resource destination) throws RepositoryException {
        destination.map(source);
    }

    @Override
    public synchronized boolean delete(UUID id) throws ObjectNotFoundException {
        Resource item = getByGuid(id);
        if (item == null) throw new ObjectNotFoundException();
        return items.remove(item);
    }
}
