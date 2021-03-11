package mappers;

import org.mapstruct.factory.Mappers;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MapperHelper {
    private final MyMapper mapper = Mappers.getMapper(MyMapper.class);

    public EntityMapper getEntityMapper() {
        return entityMapper;
    }

    private final EntityMapper entityMapper = Mappers.getMapper(EntityMapper.class);

    public MyMapper getMapper() {
        return mapper;
    }
}
