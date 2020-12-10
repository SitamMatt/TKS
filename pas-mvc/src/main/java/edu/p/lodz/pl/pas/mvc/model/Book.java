package edu.p.lodz.pl.pas.mvc.model;

import java.util.UUID;

public class Book extends Resource {
    private String author;

    public Book(String t, int pages, String au, String pH) {
        super(t, pages, pH);
        author = au;
    }

    public Book() {
        this("", 0, "", "");
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
