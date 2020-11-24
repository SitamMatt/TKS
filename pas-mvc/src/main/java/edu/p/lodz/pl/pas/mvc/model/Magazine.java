package edu.p.lodz.pl.pas.mvc.model;

public class Magazine extends Resource {
    private int number;

    public Magazine(String t, int pages, String pH, int nr){
        title = t;
        pagesCount = pages;
        number = nr;
        publishingHouse = pH;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
