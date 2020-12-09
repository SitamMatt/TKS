package edu.p.lodz.pl.pas.mvc.model;

import java.util.Date;
import java.util.UUID;

public class Event {
    private UUID id;
    private Date rentDate;
    private Date returnDate;
    private User renter;
    private Resource resource;

    public Event(UUID id, Date rentDate, User renter, Resource resource) {
        this.id = id;
        this.rentDate = rentDate;
        this.renter = renter;
        this.resource = resource;
    }

    public Date getRentDate() {
        return rentDate;
    }

    public Date getReturnDate() {
        return returnDate;
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

    public void setReturnDate(Date date){
        this.returnDate = date;
    }
}
