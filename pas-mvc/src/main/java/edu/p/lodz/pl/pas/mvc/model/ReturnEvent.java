package edu.p.lodz.pl.pas.mvc.model;

import java.util.Date;
import java.util.UUID;

public class ReturnEvent extends Event {
    private RentEvent rentEvent;

    public ReturnEvent(UUID id, Date date, User renter, Resource resource, RentEvent rentEvent) {
        super(id, date, renter, resource);
        this.rentEvent = rentEvent;
    }

    public RentEvent getRentEvent() {
        return rentEvent;
    }
}
