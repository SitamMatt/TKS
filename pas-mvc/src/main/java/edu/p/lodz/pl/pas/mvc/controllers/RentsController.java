package edu.p.lodz.pl.pas.mvc.controllers;

import edu.p.lodz.pl.pas.mvc.services.EventsService;
import edu.p.lodz.pl.pas.mvc.services.dto.EventDto;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@RequestScoped
@Named
public class RentsController {
    @Inject
    private EventsService eventsService;

    public List<EventDto> getActiveRents() {
        return eventsService.getAllActiveRents();
    }

    public List<EventDto> getArchiveRents() {
        return eventsService.getAllArchiveRents();
    }
}
