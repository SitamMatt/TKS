package edu.p.lodz.pl.pas.mvc.model;

import java.util.Date;

public class Rent {
    private int id;
    private Date rentDate;
    private Date returnDate;
    private User renter;
    private Resource resource;


    public Rent (int id, User renter, Resource resource){
        this.id = id;
        this.rentDate = new Date();
        this.returnDate = null;
        this.renter = renter;
        this.resource = resource;
        System.out.println(rentDate);
    }

    public int getId() {
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
