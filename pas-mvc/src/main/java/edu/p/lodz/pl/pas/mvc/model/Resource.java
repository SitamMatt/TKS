package edu.p.lodz.pl.pas.mvc.model;

public abstract class Resource{
    protected int id;
    protected String title;
    protected int pagesCount;
    protected String publishingHouse;

    public int getId() {
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

    public String getpHouse() {
        return publishingHouse;
    }

    public void setpHouse(String pHouse) {
        this.publishingHouse = pHouse;
    }
}