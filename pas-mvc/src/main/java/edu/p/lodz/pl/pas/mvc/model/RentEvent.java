package edu.p.lodz.pl.pas.mvc.model;

import java.util.Date;

public class RentEvent extends Event{

    public RentEvent(int id, Date date, User renter, Resource resource) {
        super(id, date, renter, resource);
    }
}
