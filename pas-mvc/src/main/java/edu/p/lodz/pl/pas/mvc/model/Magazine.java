package edu.p.lodz.pl.pas.mvc.model;

import java.util.UUID;

public class Magazine extends Resource {
    private int number;

    public Magazine(UUID id, String t, int pages, String pH, int nr){
        super(id, t, pages, pH);
        number = nr;
    }

    public Magazine() {
        this(UUID.randomUUID(), "", 0, "", 0);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
