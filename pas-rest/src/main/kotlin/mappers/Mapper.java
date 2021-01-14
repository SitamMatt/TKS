package mappers;

import model.Entity;
import model.User;
import org.modelmapper.ModelMapper;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Mapper {
    public class elo{
        int a;

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }
    }

    public Mapper() {
        mapper = new ModelMapper();
//        mapper.typeMap(User.class, User.class).addMappings(mapper -> {
//            mapper.skip(User::setPassword); // todo temporary
//        });
//        mapper.typeMap(Entity.class, Entity.class)
//                .addMappings(m -> m.skip(Entity::setGuid));
        mapper.typeMap(elo.class, elo.class)
                .addMappings(m -> m.skip(elo::setA));
    }

    private ModelMapper mapper;

    public ModelMapper getMapper() {
        return mapper;
    }
}
