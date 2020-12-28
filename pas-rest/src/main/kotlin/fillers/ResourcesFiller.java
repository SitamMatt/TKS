package fillers;


import model.Book;
import model.Magazine;
import model.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ResourcesFiller {
    public List<Resource> fillResources() {
        List<Resource> result = new ArrayList<>();
        result.add(new Book(UUID.fromString("1514f5ae-f54d-4b4f-ac97-97f32fe18cb0"), "Hurry Potter", 231, "J.K Rollin", "Big J Publish"));
        result.add(new Book(UUID.fromString("6d094ca9-94d1-480f-96ca-fce609ac4c44"), "Wieśmin", 333, "Andrew Sapkowsky", "MegaNova"));
        result.add(new Magazine(UUID.fromString("e80e22a3-5399-4a63-bbf1-b6afc9646380"), "Las Palmas", 23, "Maga Zin", "2017/05/01/K/1"));
        result.add(new Book(UUID.fromString("d929c7e8-cd04-4296-be9e-d890e9fcf19e"), "O cudownym kebabie", 666, "Norbert Gierczyk", "House of orcs"));
        result.add(new Magazine(UUID.fromString("f4308c34-4df3-48fe-a830-90ad4946acab"), "Las Palmas", 21, "Maga Zin", "2019/05/01/S/1"));
        result.add(new Magazine(UUID.fromString("5af7025e-12a4-4726-925d-b7de8f9def94"), "Świat sportu", 17, "Big J Publish", "2018/05/01/M/1"));
        result.add(new Book(UUID.fromString("c8168b00-b3e9-42da-afb9-1be9c44ceb44"), "Subway 2033", 231, "Dimitri Deafosky", "MegaNova"));
        return result;
    }
}
