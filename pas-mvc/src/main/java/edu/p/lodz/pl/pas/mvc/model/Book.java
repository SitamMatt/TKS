package edu.p.lodz.pl.pas.mvc.model;

import java.util.UUID;

public class Book extends Resource {
    private String author;

    public Book(UUID id, String t, int pages, String au, String pH){
        super(id, t, pages, pH);
        author = au;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
