package rest.api.mappers;

import common.mappers.EmailMapper;
import data.UserEntity;
import javax.annotation.processing.Generated;
import domain.model.User;
import domain.model.UserRole;
import domain.model.values.Email;
import org.mapstruct.factory.Mappers;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-04-07T00:42:33+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.9 (Oracle Corporation)"
)
public class UserMapperImpl implements UserMapper {

    private final EmailMapper emailMapper = Mappers.getMapper( EmailMapper.class );

    @Override
    public User mapEntityToDomainObject(UserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Email email = null;
        UserRole role = null;
        String password = null;
        boolean active = false;

        email = emailMapper.toEmail( entity.getEmail() );
        if ( entity.getRole() != null ) {
            role = Enum.valueOf( UserRole.class, entity.getRole() );
        }
        password = entity.getPassword();
        active = entity.getActive();

        User user = new User( email, role, password, active );

        return user;
    }

    @Override
    public UserEntity mapDomainObjectToEntity(User user) {
        if ( user == null ) {
            return null;
        }

        String email = null;
        String role = null;
        String password = null;
        boolean active = false;

        email = emailMapper.toString( user.getEmail() );
        if ( user.getRole() != null ) {
            role = user.getRole().name();
        }
        password = user.getPassword();
        active = user.getActive();

        UserEntity userEntity = new UserEntity( email, role, password, active );

        return userEntity;
    }

    @Override
    public void mapDomainObjectToEntity(User user, UserEntity entity) {
        if ( user == null ) {
            return;
        }

        entity.setEmail( emailMapper.toString( user.getEmail() ) );
        if ( user.getRole() != null ) {
            entity.setRole( user.getRole().name() );
        }
        else {
            entity.setRole( null );
        }
        entity.setPassword( user.getPassword() );
        entity.setActive( user.getActive() );
    }
}
