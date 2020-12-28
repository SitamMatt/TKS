package fillers;


import model.Event;

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
            Event e = new Event(
                    UUID.randomUUID(),
                    sdf.parse("2013-03-01"),
                    null,
                    UUID.fromString("48bb061d-0a01-4f60-bdfc-f6bac839b107"),
                    UUID.fromString("c8168b00-b3e9-42da-afb9-1be9c44ceb44")
            );
            e.setReturnDate(sdf.parse("2014-03-02"));
            result.add(e);
        } catch (ParseException e) {
            return null;
        }
        return result;
    }
}
