package model;

public class BookData {

    protected String title;
    protected int pagesCount;
    protected String publishingHouse;
    protected String author;
    protected String type;

    public BookData(String title, int pagesCount, String publishingHouse, String type, String author){
        this.title = title;
        this.pagesCount = pagesCount;
        this.publishingHouse = publishingHouse;
        this.type = type;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public int getPagesCount() {
        return pagesCount;
    }

    public String getPublishingHouse() {
        return publishingHouse;
    }

    public String getAuthor() {
        return author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPagesCount(int pagesCount) {
        this.pagesCount = pagesCount;
    }

    public void setPublishingHouse(String publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}