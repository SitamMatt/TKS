package edu.p.lodz.pl.pas.mvc.fillers;

import edu.p.lodz.pl.pas.mvc.model.Event;
import edu.p.lodz.pl.pas.mvc.model.Resource;
import edu.p.lodz.pl.pas.mvc.model.User;
import edu.p.lodz.pl.pas.mvc.repositories.ResourcesRepository;
import edu.p.lodz.pl.pas.mvc.repositories.UsersRepository;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
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
        List<Event> events = new ArrayList<>();
        Event event =  new Event(
                new Date(2012, Calendar.JANUARY, 1),
                users.get(0),
                ress.get(0));
        event.setReturnDate(new Date(2012, Calendar.MAY, 12));
        events.add(event);

        event = new Event(
                new Date(2012, Calendar.MAY, 8),
                users.get(0),
                ress.get(1));
        event.setReturnDate(new Date(2016, Calendar.OCTOBER, 20));
        events.add(event);

        event = new Event(
                new Date(2015, Calendar.MAY, 22),
                users.get(1),
                ress.get(5));
        event.setReturnDate(new Date(2017, Calendar.MAY, 13));
        events.add(event);

        return events;
    }
}
