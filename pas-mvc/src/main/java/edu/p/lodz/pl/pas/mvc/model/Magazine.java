package edu.p.lodz.pl.pas.mvc.model;

public class Magazine extends Resource {
    private int number;

    public Magazine(int id, String t, int pages, String pH, int nr){
        super(id, t, pages, pH);
        number = nr;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
