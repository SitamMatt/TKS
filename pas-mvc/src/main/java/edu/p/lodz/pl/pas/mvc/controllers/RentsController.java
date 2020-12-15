package edu.p.lodz.pl.pas.mvc.controllers;

import edu.p.lodz.pl.pas.mvc.services.EventsService;
import edu.p.lodz.pl.pas.mvc.services.dto.EventDto;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ViewScoped
@Named
public class RentsController implements Serializable {

    @Inject
    private EventsService eventsService;

    private String searchQuery;

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public List<EventDto> getActiveRents() {
        return eventsService.getAllActiveRents();
    }

    public List<EventDto> getArchiveRents() {
        if(searchQuery != null) {
            EventDto eventDto = eventsService.getAllArchiveRents().stream()
                    .filter(x -> x.getId().toString().equals(searchQuery))
                    .findFirst()
                    .orElse(null);
            if (eventDto == null) {
                return new ArrayList<>();
            }
            return Collections.singletonList(eventDto);
        }
        return eventsService.getAllArchiveRents();
    }

    public String searchArchived() {
        return "archiveRentsList.xhtml?faces-redirect=true&searchQuery=" + searchQuery;
    }
}
