package edu.p.lodz.pl.pas.mvc.model;

import java.util.Date;

public class ReturnEvent extends Event {
    private RentEvent rentEvent;

    public ReturnEvent(int id, Date date, User renter, Resource resource, RentEvent rentEvent) {
        super(id, date, renter, resource);
        this.rentEvent = rentEvent;
    }

    public RentEvent getRentEvent() {
        return rentEvent;
    }
}
