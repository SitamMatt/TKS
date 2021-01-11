package mappers;

import model.Entity;
import model.User;
import org.modelmapper.ModelMapper;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Mapper extends ModelMapper {
    public Mapper() {
        this.typeMap(User.class, User.class).addMappings(mapper -> {
            mapper.skip(User::setPassword); // todo temporary
        });
        this.typeMap(Entity.class, Entity.class).addMappings(mapper ->{
            mapper.skip(Entity::setGuid);
        });
    }
}
