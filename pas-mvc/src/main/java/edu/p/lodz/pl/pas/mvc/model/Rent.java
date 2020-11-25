package edu.p.lodz.pl.pas.mvc.model;

import java.util.Date;
import java.util.UUID;

public class Rent {
    private UUID id;
    private Date rentDate;
    private Date returnDate;
    private User renter;
    private Resource resource;


    public Rent (UUID id, User renter, Resource resource){
        this.id = id;
        this.rentDate = new Date();
        this.returnDate = null;
        this.renter = renter;
        this.resource = resource;
        System.out.println(rentDate);
    }

    public UUID getId() {
        return id;
    }

    public Date getRentDate() {
        return rentDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public User getRenter() {
        return renter;
    }

    public Resource getResource() {
        return resource;
    }

    public void endRent(){
        this.returnDate = new Date();
    }
}
