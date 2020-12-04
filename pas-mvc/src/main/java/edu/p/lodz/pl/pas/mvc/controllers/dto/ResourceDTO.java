package edu.p.lodz.pl.pas.mvc.controllers.dto;

import java.util.UUID;

public class ResourceDTO {
    private UUID id;
    private String title;
    private int pagesCount;
    private String publishingHouse;
    private int magazineNumber;
    private String author;
    private String resType;

    public ResourceDTO(UUID id, String title, int pagesCount, String publishingHouse) {
        this.id = id;
        this.title = title;
        this.pagesCount = pagesCount;
        this.publishingHouse = publishingHouse;
        this.resType = "Book";
    }

    public ResourceDTO() {
        this(UUID.randomUUID(), "", 0, "");
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public int getMagazineNumber() {
        return magazineNumber;
    }

    public void setMagazineNumber(int magazineNumber) {
        this.magazineNumber = magazineNumber;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getResType() {
        return resType;
    }

    public void setResType(String resType) {
        this.resType = resType;
    }
}
