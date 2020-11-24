package edu.p.lodz.pl.pas.mvc.model;

public class Book extends Resource {
    private String author;

    public Book(String t, int pages, String au, String pH){
        title = t;
        pagesCount = pages;
        author = au;
        publishingHouse = pH;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
