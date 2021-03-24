package mappers;

import data.UserEntity;
import model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User mapEntityToDomainObject(UserEntity entity);

    UserEntity mapDomainObjectToEntity(User user);

    void mapDomainObjectToEntity(User user, @MappingTarget UserEntity entity);
}
