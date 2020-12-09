package edu.p.lodz.pl.pas.mvc.controllers;

import edu.p.lodz.pl.pas.mvc.model.Event;
import edu.p.lodz.pl.pas.mvc.services.EventsService;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@RequestScoped
@Named
@RolesAllowed({"ADMIN", "WORKER"})
@Stateful
public class EventsController {
    @Inject
    private EventsService eventsService;

    public List<Event> getAllEvents(){
        return eventsService.getCurrentRents();
    }
}
