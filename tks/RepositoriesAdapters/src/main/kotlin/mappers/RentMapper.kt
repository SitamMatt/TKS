package mappers;

import data.RentEntity;
import model.Rent;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RentMapper {

    RentMapper INSTANCE = Mappers.getMapper(RentMapper.class);

    Rent mapEntityToDomainObject(RentEntity entity);

    RentEntity mapDomainObjectToEntity(Rent user);

    void mapDomainObjectToEntity(Rent user, @MappingTarget RentEntity entity);
}
