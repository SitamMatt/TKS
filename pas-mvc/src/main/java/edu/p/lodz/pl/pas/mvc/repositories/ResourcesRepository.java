package edu.p.lodz.pl.pas.mvc.repositories;

import edu.p.lodz.pl.pas.mvc.RefUtils;
import edu.p.lodz.pl.pas.mvc.controllers.dto.ResourceDTO;
import edu.p.lodz.pl.pas.mvc.model.Book;
import edu.p.lodz.pl.pas.mvc.model.Magazine;
import edu.p.lodz.pl.pas.mvc.model.Resource;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectAlreadyStoredException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectNotFoundException;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ResourcesRepository {
    private List<Resource> items = new ArrayList<>();

    public synchronized void add(Resource resource) throws ObjectAlreadyStoredException {
        if(resource.getId() == null) {
            assignId(resource);
        }

        if (get(resource.getId()) != null) {
            throw new ObjectAlreadyStoredException();
        }
        items.add(resource);
    }

    public synchronized Resource get(UUID id) {
        return items.stream()
            .filter(resource -> resource.getId().equals(id))
            .findAny()
            .orElse(null);
    }

    public synchronized List<Resource> getAll() {
        return new ArrayList<>(items);
    }

    public synchronized void update(UUID id, Resource resource) throws ObjectNotFoundException {
        Resource original = get(id);
        if(original == null) {
            throw new ObjectNotFoundException();
        }
        original.setPagesCount(resource.getPagesCount());
        original.setPublishingHouse(resource.getPublishingHouse());
        original.setTitle(resource.getTitle());
    }

    public synchronized boolean delete(UUID id){
        Resource resource = get(id);
        return items.remove(resource);
    }

    public static ResourceDTO toDTO(Resource resource) {
        ResourceDTO resDTO = new ResourceDTO(resource.getId(), resource.getTitle(), resource.getPagesCount(), resource.getPublishingHouse());

        if(resource instanceof Book) {
            Book res = (Book)resource;
            resDTO.setAuthor(res.getAuthor());
            resDTO.setResType("Book");
        } else if(resource instanceof Magazine) {
            Magazine res = (Magazine) resource;
            resDTO.setMagazineNumber(res.getNumber());
            resDTO.setResType("Magazine");
        }
        return resDTO;
    }

    public static Resource fromDTO(ResourceDTO resDTO) {
        Resource resource = null;

        if(resDTO.getResType().equals("Book")) {
            resource = new Book(resDTO.getTitle(), resDTO.getPagesCount(), resDTO.getAuthor(), resDTO.getPublishingHouse());
        } else if(resDTO.getResType().equals("Magazine")) {
            resource = new Magazine(resDTO.getTitle(), resDTO.getPagesCount(), resDTO.getPublishingHouse(), resDTO.getMagazineNumber());
        }

        if(resource != null) {
            try {
                RefUtils.setFieldValue(resource, "id", UUID.randomUUID());
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return resource;
        }
        throw new RuntimeException("This shouldn't be executed!");
    }

    private void assignId(Resource resource) {
        try {
            RefUtils.setFieldValue(resource, "id", UUID.randomUUID());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
