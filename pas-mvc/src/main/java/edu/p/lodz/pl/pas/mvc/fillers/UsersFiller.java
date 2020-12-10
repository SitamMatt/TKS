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
        List<User> users = new ArrayList<>();
        users.add(new User(
                "Łukasz",
                "Stanisławowski",
                UserRole.CLIENT,
                "testo",
                "test0"
                ));
        users.add(new User(
                "Jayne",
                "Najera",
                UserRole.CLIENT,
                "user",
                "user0"
        ));
        users.add(new User(
                "John",
                "Cena",
                UserRole.WORKER,
                "worker",
                "worker0"
        ));
        users.add(new User(
                "Janusz",
                "Pawlacz",
                UserRole.ADMIN,
                "admin",
                "admin0"
        ));

        return users;
    }
}
