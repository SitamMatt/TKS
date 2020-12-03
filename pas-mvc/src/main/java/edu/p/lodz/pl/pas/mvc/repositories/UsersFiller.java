package edu.p.lodz.pl.pas.mvc.repositories;

import edu.p.lodz.pl.pas.mvc.model.Type;
import edu.p.lodz.pl.pas.mvc.model.User;

import javax.enterprise.context.Dependent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Dependent
public class UsersFiller {
    public List<User> fillUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User(
                UUID.randomUUID(),
                "Łukasz",
                "Stanisławowski",
                Type.CLIENT,
                "testo",
                "test0"
                ));
        users.add(new User(
                UUID.randomUUID(),
                "Jayne",
                "Najera",
                Type.CLIENT,
                "user",
                "user0"
        ));
        users.add(new User(
                UUID.randomUUID(),
                "John",
                "Cena",
                Type.WORKER,
                "worker",
                "worker0"
        ));
        users.add(new User(
                UUID.randomUUID(),
                "Janusz",
                "Pawlacz",
                Type.ADMIN,
                "admin",
                "admin0"
        ));

        return users;
    }
}
