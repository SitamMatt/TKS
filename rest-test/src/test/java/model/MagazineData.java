package model;

public class MagazineData {
    protected String title;
    protected int pagesCount;
    protected String publishingHouse;
    protected String issueDate;
    protected String type;

    public MagazineData(String title, int pagesCount, String publishingHouse, String type, String issueDate){
        this.title = title;
        this.pagesCount = pagesCount;
        this.publishingHouse = publishingHouse;
        this.type = type;
        this.issueDate = issueDate;
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

    public String getIssueDate() {
        return issueDate;
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

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }
}
