package edu.p.lodz.pl.pas.mvc;

import edu.p.lodz.pl.pas.mvc.model.UserRole;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class RolesConverter {
    public static Set<String> getRolesFromEnum(UserRole possibleUserRole){
        return new HashSet<String>(Collections.singleton(possibleUserRole.name()));
//        return Arrays.stream(possibleType).map(Enum::name).collect(Collectors.toSet());
    }
}
