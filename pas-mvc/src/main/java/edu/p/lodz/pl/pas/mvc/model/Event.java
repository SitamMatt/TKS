package edu.p.lodz.pl.pas.mvc.model;

import java.util.Date;
import java.util.UUID;

public abstract class Event {
    private UUID id;
    private Date date;
    private User renter;
    private Resource resource;

    public Event(UUID id, Date date, User renter, Resource resource) {
        this.id = id;
        this.date = date;
        this.renter = renter;
        this.resource = resource;
    }

    public Date getDate() {
        return date;
    }

    public UUID getId() {
        return id;
    }

    public User getRenter() {
        return renter;
    }

    public Resource getResource() {
        return resource;
    }
}
