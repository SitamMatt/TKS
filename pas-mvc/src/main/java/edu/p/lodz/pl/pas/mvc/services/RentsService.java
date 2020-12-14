package edu.p.lodz.pl.pas.mvc.services;

import edu.p.lodz.pl.pas.mvc.model.Event;
import edu.p.lodz.pl.pas.mvc.model.Resource;
import edu.p.lodz.pl.pas.mvc.repositories.interfaces.IEventsRepository;
import edu.p.lodz.pl.pas.mvc.repositories.interfaces.IResourcesRepository;
import edu.p.lodz.pl.pas.mvc.repositories.interfaces.IUsersRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestScoped
public class RentsService {
    @Inject
    private IResourcesRepository resourcesRepository;
    @Inject
    private IEventsRepository eventsRepository;
    @Inject
    private IUsersRepository usersRepository;

    public List<Resource> getAvailableResources() {
        return null;
//        return resourcesRepository.getAll().stream()
//                .filter(res -> eventsRepository.isAvailable(res.getId()))
//                .collect(Collectors.toList());
    }

    public void rent(UUID id) {
//        if(eventsRepository.isAvailable(id)) {
//            Event ev = new Event(new Date(),
//                    usersRepository.findUserByLogin(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName()),
//                    resourcesRepository.get(id));
//            eventsRepository.add(ev);
//        }
    }
}
