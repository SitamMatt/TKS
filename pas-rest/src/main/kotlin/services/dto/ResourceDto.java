package services.dto;

import java.util.UUID;

public class ResourceDto {
    private UUID id;
    private String title;
    private int pagesCount;
    private String publishingHouse;
    private String issueDate;
    private String author;
    private String type;

    public ResourceDto(UUID id, String title, int pagesCount, String publishingHouse, String issueDate, String author, String type) {
        this.id = id;
        this.title = title;
        this.pagesCount = pagesCount;
        this.publishingHouse = publishingHouse;
        this.issueDate = issueDate;
        this.author = author;
        this.type = type;
    }

    public ResourceDto() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public UUID getId() {
        return id;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
