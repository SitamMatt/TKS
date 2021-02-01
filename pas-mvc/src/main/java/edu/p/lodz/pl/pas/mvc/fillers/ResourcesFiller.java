package edu.p.lodz.pl.pas.mvc.fillers;

import edu.p.lodz.pl.pas.mvc.model.Book;
import edu.p.lodz.pl.pas.mvc.model.Magazine;
import edu.p.lodz.pl.pas.mvc.model.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ResourcesFiller {
    public List<Resource> fillResources() {
        List<Resource> result = new ArrayList<>();
        result.add(new Book(UUID.fromString("1514f5ae-f54d-4b4f-ac97-97f32fe18cb0"), "Hurry Potter", 231, "Big J Publish", "J.K Rollin"));
        result.add(new Book(UUID.fromString("6d094ca9-94d1-480f-96ca-fce609ac4c44"), "Wieśmin", 333, "MegaNova", "Andrew Sapkowsky"));
        result.add(new Magazine(UUID.fromString("e80e22a3-5399-4a63-bbf1-b6afc9646380"), "Las Palmas", 23, "Maga Zin", "2017/05/01/K/1"));
        result.add(new Book(UUID.fromString("d929c7e8-cd04-4296-be9e-d890e9fcf19e"), "O cudownym kebabie", 666, "House of orcs", "Norbert Gierczyk"));
        result.add(new Magazine(UUID.fromString("f4308c34-4df3-48fe-a830-90ad4946acab"), "Las Palmas", 21, "Maga Zin", "2019/05/01/S/1"));
        result.add(new Magazine(UUID.fromString("5af7025e-12a4-4726-925d-b7de8f9def94"), "Świat sportu", 17, "Big J Publish", "2018/05/01/M/1"));
        result.add(new Book(UUID.fromString("c8168b00-b3e9-42da-afb9-1be9c44ceb44"), "Subway 2033", 231, "MegaNova", "Dimitri Deafosky"));
        result.add(new Book(UUID.fromString("12a5fc20-63f3-11eb-ae93-0242ac130002"), "Hyperion", 504, "MAG", "Dan Simmons"));
        result.add(new Magazine(UUID.fromString("74fc749e-63f3-11eb-ae93-0242ac130002"), "Nowa Fantastyka", 98, "Proszynski Media", "2017/10/01/J/1"));
        result.add(new Book(UUID.fromString("7ae52e8c-63f3-11eb-ae93-0242ac130002"), "Bohaterowie", 752, "MAG", "Joe Abercrombie"));
        result.add(new Book(UUID.fromString("3c0f0b2d-a230-4c39-bce5-32ff3b0b86d0"), "Pan Lodowego Ogrodu Tom I", 492, "Fabryka Słów", "Jarosław Grzędowicz"));
        result.add(new Magazine(UUID.fromString("e4d15976-430f-4bf4-b1e7-c1c405e4d929"), "FANTOM", 108, "Skarpa Warszawska", "2017/04/01/W/1"));
        result.add(new Book(UUID.fromString("b2669a87-8f6a-41aa-b2ca-a0b687552440"), "Kolor magii", 208, "Prószyński i S-ka", "Terry Pratchett"));
        result.add(new Book(UUID.fromString("8e5dc0fe-1094-41a2-bdab-6ce3c83c953b"), "Chirurg", 384, "Albatros", "Tess Gerritsen"));
        result.add(new Book(UUID.fromString("5af8e27b-748d-4d5f-8788-abbc7cd38ed4"), "Gwiezdny pył", 200, "MAG", "Neil Gaiman"));
        result.add(new Book(UUID.fromString("427839ce-5184-4a87-9d62-14efab8b6da4"), "W głębi lasu", 367, "Albatros", "Harlan Coben"));
        return result;
    }
}
