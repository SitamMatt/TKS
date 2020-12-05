package edu.p.lodz.pl.pas.mvc.model;

import java.util.UUID;

public abstract class Resource {
    protected UUID id;
    protected String title;
    protected int pagesCount;
    protected String publishingHouse;

    public Resource(UUID id, String title, int pagesCount, String publishingHouse) {
        this.id = id;
        this.title = title;
        this.pagesCount = pagesCount;
        this.publishingHouse = publishingHouse;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getPagesCount() {
        return pagesCount;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPagesCount(int pagesCount) {
        this.pagesCount = pagesCount;
    }

    public String getPublishingHouse() {
        return publishingHouse;
    }

    public void setPublishingHouse(String pHouse) {
        this.publishingHouse = pHouse;
    }

}