package edu.p.lodz.pl.pas.mvc.fillers;

import edu.p.lodz.pl.pas.mvc.model.Book;
import edu.p.lodz.pl.pas.mvc.model.Magazine;
import edu.p.lodz.pl.pas.mvc.model.Resource;

import java.util.ArrayList;
import java.util.List;

public class ResourcesFiller {
    public List<Resource> fillResources() {
        List<Resource> ress = new ArrayList<>();
        ress.add(new Book("Hurry Potter", 231, "J.K Rollin", "Big J Publish"));
        ress.add(new Book("Wieśmin", 333, "Andrew Sapkowsky", "MegaNova"));
        ress.add(new Magazine("Las Palmas", 23, "Maga Zin", 3));
        ress.add(new Book("O cudownym kebabie", 666, "Norbert Gierczyk", "House of orcs"));
        ress.add(new Magazine("Las Palmas", 21, "Maga Zin", 5));
        ress.add(new Magazine("Świat sportu", 17, "Big J Publish", 69));
        ress.add(new Book("Subway 2033", 231, "Dimitri Deafosky", "MegaNova"));
        return ress;
    }
}
