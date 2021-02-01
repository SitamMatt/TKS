package edu.p.lodz.pl.pas.mvc.repositories;

import edu.p.lodz.pl.pas.mvc.fillers.ResourcesFiller;
import edu.p.lodz.pl.pas.mvc.model.Resource;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectNotFoundException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.RepositoryException;
import edu.p.lodz.pl.pas.mvc.repositories.interfaces.IResourcesRepository;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
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
        Resource item = getById(id);
        if (item == null) throw new ObjectNotFoundException();
        return items.remove(item);
    }

    @Override
    public ArrayList<Resource> getSelectedResources(int pageSize, int pageNumber){
        ArrayList<Resource> resourceArrayList = new ArrayList<>();
        for (int i=0; i<items.size(); i++){
            if ((i>=(pageSize*(pageNumber-1))) && (i<(pageSize*pageNumber))){
                resourceArrayList.add(items.get(i));
            }
        }
        return resourceArrayList;
    }
}
