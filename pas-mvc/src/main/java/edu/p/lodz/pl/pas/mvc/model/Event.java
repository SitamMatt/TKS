package edu.p.lodz.pl.pas.mvc.model;

import java.util.Date;
import java.util.UUID;

public class Event extends Entity {
    private Date rentDate;
    private Date returnDate;
    private UUID userId;
    private UUID resourceId;

    public Event(UUID id, Date rentDate, Date returnDate, UUID userId, UUID resourceId) {
        super(id);
        this.rentDate = rentDate;
        this.returnDate = returnDate;
        this.userId = userId;
        this.resourceId = resourceId;
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

    public UUID getUserId() {
        return userId;
    }

    public UUID getResourceId() {
        return resourceId;
    }

    public void setResourceId(UUID resourceId) {
        this.resourceId = resourceId;
    }

    public void map(Event source) {
        this.rentDate = source.rentDate;
        this.returnDate = source.returnDate;
        this.resourceId = source.resourceId;
        this.userId = source.userId;
    }
}
