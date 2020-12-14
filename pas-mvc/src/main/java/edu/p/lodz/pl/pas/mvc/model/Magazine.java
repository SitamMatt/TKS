package edu.p.lodz.pl.pas.mvc.model;

import edu.p.lodz.pl.pas.mvc.model.exceptions.IncompatibleTypeExeption;

import java.util.UUID;

public class Magazine extends Resource {
    private String issueDate;

    public Magazine(UUID id, String title, int pagesCount, String publishingHouse, String issueDate) {
        super(id, title, pagesCount, publishingHouse);
        this.issueDate = issueDate;
    }

    @Override
    public void map(Resource source) throws IncompatibleTypeExeption {
        super.map(source);
        this.issueDate = ((Magazine) source).issueDate;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }
}
