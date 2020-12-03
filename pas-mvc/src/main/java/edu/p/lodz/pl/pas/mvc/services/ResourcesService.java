package edu.p.lodz.pl.pas.mvc.services;

import edu.p.lodz.pl.pas.mvc.model.Resource;
import edu.p.lodz.pl.pas.mvc.repositories.InMemoryUsersRepository;
import edu.p.lodz.pl.pas.mvc.repositories.ResourcesRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;

@Named
@RequestScoped
public class ResourcesService {
    @Inject
    private ResourcesRepository usersRepository;

    public void add(Resource resource) {
        usersRepository.add(resource);
    }

    public Resource get(UUID id) {
        return usersRepository.get(id);
    }

    public void update(UUID id, Resource resource) {
        usersRepository.update(id, resource);
    }

    public boolean delete(UUID id) {
        return usersRepository.delete(id);
    }
}
