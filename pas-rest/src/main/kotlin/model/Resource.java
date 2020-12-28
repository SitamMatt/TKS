package model;

import model.exceptions.IncompatibleTypeExeption;

import java.util.UUID;

public abstract class Resource extends Entity {
    protected String title;
    protected int pagesCount;
    protected String publishingHouse;

    public Resource(UUID id, String title, int pagesCount, String publishingHouse) {
        super(id);
        this.title = title;
        this.pagesCount = pagesCount;
        this.publishingHouse = publishingHouse;
    }

    public void map(Resource source) throws IncompatibleTypeExeption {
        if(this.getClass() != source.getClass()) throw new IncompatibleTypeExeption();
        this.pagesCount = source.getPagesCount();
        this.title = source.getTitle();
        this.publishingHouse = source.getPublishingHouse();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPagesCount() {
        return pagesCount;
    }

    public void setPagesCount(int pagesCount) {
        this.pagesCount = pagesCount;
    }

    public String getPublishingHouse() {
        return publishingHouse;
    }

    public void setPublishingHouse(String publishingHouse) {
        this.publishingHouse = publishingHouse;
    }
}