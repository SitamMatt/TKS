package edu.p.lodz.pl.pas.mvc.repositories;

import edu.p.lodz.pl.pas.mvc.model.User;

import javax.enterprise.context.Dependent;
import java.util.ArrayList;

@Dependent
public class UsersFiller {
    // TODO: Implementacja źródeł danych
    public ArrayList<User> fillUsers() {
        return new ArrayList<>();
    }
}
