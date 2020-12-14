package edu.p.lodz.pl.pas.mvc.fillers;

import edu.p.lodz.pl.pas.mvc.model.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EventsFiller {
    public List<Event> fillEvents() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        List<Event> result = new ArrayList<>();
        try {
            result.add(new Event(
                    UUID.fromString("fcb2c040-5b92-463d-bad8-818937d4f450"),
                    sdf.parse("2012-01-01"),
                    null,
                    UUID.fromString("48bb061d-0a01-4f60-bdfc-f6bac839b107"),
                    UUID.fromString("1514f5ae-f54d-4b4f-ac97-97f32fe18cb0")
            ));
        } catch (ParseException e) {
            return null;
        }
        return result;
    }
}
