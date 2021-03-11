package services;


import dto.EventDto;
import exceptions.ObjectNotFoundException;
import mappers.MapperHelper;
import repositories.interfaces.IEventsRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestScoped
public class EventsService {
    @Inject private IEventsRepository eventsRepository;
    @Inject private MapperHelper helper;

    public dto.EventDto find(UUID id) throws ObjectNotFoundException {
        var event = eventsRepository.getByGuid(id);
        if (event == null) throw new ObjectNotFoundException();
        var dto = helper.getMapper().mapEventToDto(event);
        return dto;
    }

    public List<EventDto> filter(String type, int page, int maxResults, String search) {
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
        return stream.map(x -> helper.getMapper().mapEventToDto(x))
                .collect(Collectors.toList());
    }
}
