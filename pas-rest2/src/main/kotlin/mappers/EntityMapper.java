package mappers;

import model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface EntityMapper {

    @Mapping(target = "guid", ignore = true)
    default void fromResource(Resource source, @MappingTarget Resource target){
        if(source instanceof Magazine) fromResource((Magazine) source, (Magazine) target);
        else if(source instanceof Book) fromResource((Book) source, (Book) target);
    }

    void fromResource(Book source, @MappingTarget Book target);

    void fromResource(Magazine source, @MappingTarget Magazine target);

    @Mapping(target = "guid", ignore = true)
    User map(User source, @MappingTarget User destination);

    @Mapping(target = "guid", ignore = true)
    void map(Event source, @MappingTarget Event destination);
}
