package edu.p.lodz.pl.pas.mvc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListUtil {
    public static <T extends Copyable> List<T> deepCopy(List<T> list) throws CloneNotSupportedException {
        ArrayList<T> clone = new ArrayList<>();
        for (T t : list) {
            clone.add((T) t.clone());
        }
        return clone;
    }
}
