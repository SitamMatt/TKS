package edu.p.lodz.pl.pas.mvc.controllers;

import edu.p.lodz.pl.pas.mvc.services.EventsService;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@ViewScoped
@Named
@RolesAllowed({"ADMIN", "WORKER"})
@Stateful
public class EventFormController {
    @Inject
    private EventsService eventsService;

    private String eventID;
}
