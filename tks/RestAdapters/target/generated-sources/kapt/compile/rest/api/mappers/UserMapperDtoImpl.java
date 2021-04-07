package rest.api.mappers;

import common.mappers.EmailMapper;
import rest.api.dto.UserDto;
import javax.annotation.processing.Generated;
import domain.model.User;
import domain.model.UserRole;
import domain.model.values.Email;
import org.mapstruct.factory.Mappers;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-04-07T00:42:39+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.9 (Oracle Corporation)"
)
public class UserMapperDtoImpl implements UserMapperDto {

    private final EmailMapper emailMapper = Mappers.getMapper( EmailMapper.class );

    @Override
    public User toDomainObject(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        Email email = null;
        UserRole role = null;
        String password = null;
        boolean active = false;

        email = emailMapper.toEmail( userDto.getEmail() );
        if ( userDto.getRole() != null ) {
            role = Enum.valueOf( UserRole.class, userDto.getRole() );
        }
        password = userDto.getPassword();
        active = userDto.isActive();

        User user = new User( email, role, password, active );

        return user;
    }

    @Override
    public UserDto toDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setEmail( emailMapper.toString( user.getEmail() ) );
        userDto.setPassword( user.getPassword() );
        userDto.setActive( user.getActive() );
        if ( user.getRole() != null ) {
            userDto.setRole( user.getRole().name() );
        }

        return userDto;
    }
}
