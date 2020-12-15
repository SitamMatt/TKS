package edu.p.lodz.pl.pas.mvc.controllers;

import edu.p.lodz.pl.pas.mvc.services.EventsService;
import edu.p.lodz.pl.pas.mvc.services.dto.EventDto;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@ViewScoped
@Named
public class RentsController implements Serializable {


    @Inject
    private EventsService eventsService;

    public List<EventDto> getActiveRents() {
        return eventsService.getAllActiveRents();
    }

    public List<EventDto> getArchiveRents() {
        return eventsService.getAllArchiveRents();
    }
}
