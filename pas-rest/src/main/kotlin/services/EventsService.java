package services;


import exceptions.ObjectNotFoundException;
import mappers.Mapper;
import mappers.MapperHelper;
import model.Event;
import repositories.interfaces.IEventsRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestScoped
public class EventsService {
    @Inject private IEventsRepository eventsRepository;
    @Inject private Mapper mapper;
    @Inject private MapperHelper helper;

    public dto.EventDto find(UUID id) throws ObjectNotFoundException {
        var event = eventsRepository.getByGuid(id);
        if (event == null) throw new ObjectNotFoundException();
//        return mapper.getMapper().map(event, dto.EventDto.class);
        return helper.getMapper().mapEventToDto(event);
    }

//    public List<EventDto> getUserActiveRents(UUID id) {
//        return eventsRepository
//                .getUserActiveRents(id)
//                .stream()
//                .map(this::map)
//                .collect(Collectors.toList());
//    }

    public List<Event> filter(String type, int page, int maxResults, String search) {
        if(page != 0 && maxResults == 0) maxResults = eventsRepository.count() / page;
        var stream = eventsRepository.getPaged(page, maxResults).stream();
        if(type != null) switch(type){
            case "active":
                stream = stream.filter(x -> x.getReturnDate() == null);
                break;
            case "archive":
                stream = stream.filter(x -> x.getReturnDate() != null);
                break;
        }
        if(search != null){
            stream = stream.filter(x -> x.getGuid().toString().contains(search));
        }
        return stream.collect(Collectors.toList());
    }
}
