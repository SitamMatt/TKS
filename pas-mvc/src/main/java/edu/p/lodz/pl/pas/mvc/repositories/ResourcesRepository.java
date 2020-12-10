package edu.p.lodz.pl.pas.mvc.repositories;

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
        if(resDTO.getResType().equals("Book")) {
            return new Book(resDTO.getId(), resDTO.getTitle(), resDTO.getPagesCount(), resDTO.getAuthor(), resDTO.getPublishingHouse());
        } else if(resDTO.getResType().equals("Magazine")) {
            return new Magazine(resDTO.getId(), resDTO.getTitle(), resDTO.getPagesCount(), resDTO.getPublishingHouse(), resDTO.getMagazineNumber());
        }
        throw new ArrayIndexOutOfBoundsException();
//        return null;
    }
}
