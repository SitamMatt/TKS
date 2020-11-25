package edu.p.lodz.pl.pas.mvc.model;

import java.util.Date;
import java.util.UUID;

public class RentEvent extends Event{

    public RentEvent(UUID id, Date date, User renter, Resource resource) {
        super(id, date, renter, resource);
    }
}
