package mappers;

import dto.EventDto;
import dto.ResourceGetDto;
import dto.ResourceType;
import model.Book;
import model.Event;
import model.Magazine;
import model.Resource;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface MyMapper {

    EventDto mapEventToDto(Event event);

    default ResourceGetDto mapResourceToDto(Resource resource){
        if(resource instanceof Magazine) return mapResourceToDto((Magazine) resource);
        else if(resource instanceof Book) return mapResourceToDto((Book) resource);
        return null;
    }

    @AfterMapping
    default void resourceToType(@MappingTarget ResourceGetDto dto, Resource resource){
        if(resource instanceof Magazine) dto.setType(ResourceType.Magazine);
        else if(resource instanceof Book) dto.setType(ResourceType.Book);
    }



    ResourceGetDto mapResourceToDto(Magazine magazine);
    ResourceGetDto mapResourceToDto(Book book);
}
