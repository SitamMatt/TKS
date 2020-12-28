package model;

import model.exceptions.IncompatibleTypeExeption;

import java.util.UUID;

public class Book extends Resource {
    private String author;

    public Book(UUID id, String title, int pagesCount, String publishingHouse, String author) {
        super(id, title, pagesCount, publishingHouse);
        this.author = author;
    }

    @Override
    public void map(Resource source) throws IncompatibleTypeExeption {
        super.map(source);
        this.author = ((Book) source).getAuthor();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
