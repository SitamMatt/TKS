package edu.p.lodz.pl.pas.mvc.services;

import edu.p.lodz.pl.pas.mvc.model.Resource;
import edu.p.lodz.pl.pas.mvc.repositories.interfaces.IEventsRepository;
import edu.p.lodz.pl.pas.mvc.repositories.interfaces.IResourcesRepository;
import edu.p.lodz.pl.pas.mvc.repositories.interfaces.IUsersRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class RentsService {
    @Inject
    private IResourcesRepository resourcesRepository;
    @Inject
    private IEventsRepository eventsRepository;
    @Inject
    private IUsersRepository usersRepository;

    public List<Resource> getAvailableResources() {
//        List<Resource> blocked =  eventsRepository.getAll().stream()
//                .filter(x -> x.getReturnDate() == null)
//                .map(Event::getResource)
//                .collect(Collectors.toList());
        return resourcesRepository.getAll();
    }

    public synchronized void rent(UUID id) {
        if(eventsRepository.isAvailable(id)){
//            Event ev = new Event(UUID.randomUUID(), new Date(), usersRepository.findUserByLogin(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName()), resourcesRepository.get(id));
        }
    }
}
