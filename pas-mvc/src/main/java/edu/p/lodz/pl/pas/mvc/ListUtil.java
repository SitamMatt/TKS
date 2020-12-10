package edu.p.lodz.pl.pas.mvc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListUtil {
    public static <T extends Copyable> List<T> deepCopy(List<T> list) {
        ArrayList<T> clone = new ArrayList<>();
        for (T t : list) {
            try {
                clone.add((T) t.clone());
            } catch (CloneNotSupportedException ignored) { }
        }
        return clone;
    }
}
