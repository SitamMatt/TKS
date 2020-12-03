package edu.p.lodz.pl.pas.mvc;

import edu.p.lodz.pl.pas.mvc.model.Type;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class RolesConverter {
    public static Set<String> getRolesFromEnum(Type possibleType){
        return new HashSet<String>(Collections.singleton(possibleType.name()));
//        return Arrays.stream(possibleType).map(Enum::name).collect(Collectors.toSet());
    }
}
