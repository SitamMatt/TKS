package edu.p.lodz.pl.pas.mvc.fillers;

import edu.p.lodz.pl.pas.mvc.model.UserRole;
import edu.p.lodz.pl.pas.mvc.model.User;

import javax.enterprise.context.Dependent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Dependent
public class UsersFiller {
    public List<User> fillUsers() {
        List<User> result = new ArrayList<>();
        result.add(new User(
                UUID.fromString("f9b3a442-b637-4e23-ad71-910ee816453e"),
                true,
                UserRole.CLIENT,
                "Łukasz",
                "Stanisławowski",
                "testo",
                "test0"
                ));
        result.add(new User(
                UUID.fromString("48bb061d-0a01-4f60-bdfc-f6bac839b107"),
                true,
                UserRole.CLIENT,
                "Jayne",
                "Najera",
                "user",
                "user0"
        ));
        result.add(new User(
                UUID.randomUUID(),
                true,
                UserRole.WORKER,
                "Adam",
                "Mickiewicz",
                "worker1",
                "worker1"
        ));
        result.add(new User(
                UUID.fromString("411fc900-f762-4081-90b6-e17fb32d127f"),
                true,
                UserRole.WORKER,
                "John",
                "Cena",
                "worker",
                "worker0"
        ));
        result.add(new User(
                UUID.fromString("3fbabdb6-7a44-4b9e-be8d-dd120a271b5b"),
                true,
                UserRole.ADMIN,
                "Janusz",
                "Pawlacz",
                "admin",
                "admin0"
        ));
        result.add(new User(
                UUID.fromString("9c47f5f4-cbdc-414d-8f17-3c4b035f8899"),
                true,
                UserRole.CLIENT,
                "Mateusz",
                "Szewc",
                "matt",
                "1234"
        ));

        return result;
    }
}
