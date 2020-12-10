package edu.p.lodz.pl.pas.mvc.fillers;

import edu.p.lodz.pl.pas.mvc.model.Event;
import edu.p.lodz.pl.pas.mvc.model.Resource;
import edu.p.lodz.pl.pas.mvc.model.User;
import edu.p.lodz.pl.pas.mvc.repositories.ResourcesRepository;
import edu.p.lodz.pl.pas.mvc.repositories.UsersRepository;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EventsFiller {
    @Inject
    private UsersRepository usersRepository;
    @Inject
    private ResourcesRepository resourcesRepository;

    private List<User> users;
    private List<Resource> ress;

    @PostConstruct
    private void init() {
        users = usersRepository.getAll();
        ress = resourcesRepository.getAll();
    }

    public List<Event> fillEvents() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        List<Event> events = new ArrayList<>();
        try {
            Event event =  new Event(
                    sdf.parse("2012-01-01"),
                    users.get(0),
                    ress.get(0));
            event.setReturnDate(sdf.parse("2012-04-12"));
            events.add(event);

            event = new Event(
                    sdf.parse("2012-04-08"),
                    users.get(0),
                    ress.get(1));
            event.setReturnDate(sdf.parse("2016-09-20"));
            events.add(event);

            event = new Event(
                    sdf.parse("2015-04-22"),
                    users.get(1),
                    ress.get(5));
            event.setReturnDate(sdf.parse("2017-04-13"));
            events.add(event);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return events;
    }
}
