package edu.p.lodz.pl.pas.mvc.controllers;

import edu.p.lodz.pl.pas.mvc.model.Event;
import edu.p.lodz.pl.pas.mvc.model.User;
import edu.p.lodz.pl.pas.mvc.services.EventsService;
import edu.p.lodz.pl.pas.mvc.services.RentsService;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.UUID;

@RequestScoped
@Named
@RolesAllowed({"ADMIN", "WORKER", "CLIENT"})
@Stateful
public class EventsController {
    @Inject
    private EventsService eventsService;

    public List<Event> getAllEvents(){
        return null;
//        return eventsService.getCurrentRents();
    }

    public List<Event> getAllArchiveEvents() {
        return null;
//        return eventsService.getArchiveRents();
    }

    public List<Event> getUserEvents(User user){
        return null;
//        return eventsService.getUserRents(user);
    }

    public List<Event> getCurrentUserEvents(User user){
        return null;
//        return eventsService.getCurrentUserRents(user);
    }

    public void returnResource(UUID id){
//        eventsService.finishRent(id);
    }
}
