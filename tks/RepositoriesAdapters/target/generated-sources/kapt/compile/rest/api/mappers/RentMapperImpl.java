package rest.api.mappers;

import data.AbstractResourceEntity;
import data.RentEntity;
import data.UserEntity;
import java.util.Date;
import java.util.UUID;
import javax.annotation.processing.Generated;
import domain.model.Rent;
import domain.model.values.AccessionNumber;
import domain.model.values.Email;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-04-07T00:42:33+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.9 (Oracle Corporation)"
)
public class RentMapperImpl implements RentMapper {

    @Override
    public Rent mapEntityToDomainObject(RentEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UUID id = null;
        Date endDate = null;
        Date startDate = null;

        id = entity.getId();
        endDate = entity.getEndDate();
        startDate = entity.getStartDate();

        Email userEmail = null;
        AccessionNumber resourceId = null;

        Rent rent = new Rent( id, startDate, endDate, userEmail, resourceId );

        return rent;
    }

    @Override
    public RentEntity mapDomainObjectToEntity(Rent user) {
        if ( user == null ) {
            return null;
        }

        UUID id = null;
        Date endDate = null;
        Date startDate = null;

        id = user.getId();
        endDate = user.getEndDate();
        startDate = user.getStartDate();

        UserEntity user1 = null;
        AbstractResourceEntity resource = null;

        RentEntity rentEntity = new RentEntity( id, startDate, endDate, user1, resource );

        return rentEntity;
    }

    @Override
    public void mapDomainObjectToEntity(Rent user, RentEntity entity) {
        if ( user == null ) {
            return;
        }

        entity.setId( user.getId() );
        entity.setEndDate( user.getEndDate() );
    }
}
