package edu.p.lodz.pl.pas.mvc.repositories;

import edu.p.lodz.pl.pas.mvc.model.User;

import javax.enterprise.context.Dependent;
import java.util.ArrayList;
import java.util.List;

@Dependent
public class UsersFiller {
    // TODO: Implementacja źródeł danych
    public List<User> fillUsers() {
        return new ArrayList<>();
    }
}
