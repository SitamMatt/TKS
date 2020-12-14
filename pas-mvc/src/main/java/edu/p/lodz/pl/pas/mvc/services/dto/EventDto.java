package edu.p.lodz.pl.pas.mvc.services.dto;

import edu.p.lodz.pl.pas.mvc.model.Resource;
import edu.p.lodz.pl.pas.mvc.model.User;

import java.util.Date;
import java.util.UUID;

public class EventDto {
    private final UUID id;
    private Date rentDate;
    private Date returnDate;
    private User renter;
    private Resource resource;

    public EventDto(UUID id, Date rentDate, Date returnDate, User renter, Resource resource) {
        this.id = id;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
        this.renter = renter;
        this.resource = resource;
    }

    public UUID getId() {
        return id;
    }

    public Date getRentDate() {
        return rentDate;
    }

    public void setRentDate(Date rentDate) {
        this.rentDate = rentDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public User getRenter() {
        return renter;
    }

    public void setRenter(User renter) {
        this.renter = renter;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}
