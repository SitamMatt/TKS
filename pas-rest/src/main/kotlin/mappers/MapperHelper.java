package mappers;

import org.mapstruct.factory.Mappers;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MapperHelper {
    private final MyMapper mapper = Mappers.getMapper(MyMapper.class);

    public MyMapper getMapper() {
        return mapper;
    }
}
