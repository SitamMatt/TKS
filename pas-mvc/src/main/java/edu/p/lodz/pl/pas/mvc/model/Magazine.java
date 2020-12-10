package edu.p.lodz.pl.pas.mvc.model;

public class Magazine extends Resource {
    private int number;

    public Magazine(String t, int pages, String pH, int nr){
        super(t, pages, pH);
        number = nr;
    }

    public Magazine() {
        this("", 0, "", 0);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
