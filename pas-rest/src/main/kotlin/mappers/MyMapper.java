package mappers;

import dto.EventDto;
import dto.ResourceGetDto;
import model.Event;
import model.Resource;
import org.mapstruct.Mapper;

@Mapper
public interface MyMapper {

    EventDto mapEventToDto(Event event);

    ResourceGetDto mapResourceToDto(Resource resource);
}
