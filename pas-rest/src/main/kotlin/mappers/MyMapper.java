package mappers;

import dto.*;
import model.*;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface MyMapper {

    EventDto mapEventToDto(Event event);

    UserGetDto mapUserToDto(User user);

    User mapDtoToUser(UserCreateDto dto);

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

    default Resource mapDtoToResource(ResourceBaseDto dto){
        if(dto.getType() == ResourceType.Magazine) return mapDtoToMagazine(dto);
        else if(dto.getType() == ResourceType.Book) return mapDtoToBook(dto);
        else return null;
    }

    Book mapDtoToBook(ResourceBaseDto dto);
    Magazine mapDtoToMagazine(ResourceBaseDto dto);
    ResourceGetDto mapResourceToDto(Magazine magazine);
    ResourceGetDto mapResourceToDto(Book book);
}
