package edu.p.lodz.pl.pas.mvc.repositories;

import edu.p.lodz.pl.pas.mvc.model.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ResourcesRepository {
    List<Resource> items = new ArrayList<>();

    public void add(Resource resource){
        items.add(resource);
    }

    public Resource get(UUID id){
        return items.stream()
            .filter(resource -> resource.getId() == id)
            .findAny()
            .orElse(null);
    }

    public void update(UUID id, Resource resource){
        Resource original = get(id);
        if(original == null) return; //todo return error or sth
        original.setPagesCount(resource.getPagesCount());
        original.setPublishingHouse(resource.getPublishingHouse());
        original.setTitle(resource.getTitle());
    }

    public boolean delete(UUID id){
        Resource resource = get(id);
        return items.remove(resource);
    }
}
