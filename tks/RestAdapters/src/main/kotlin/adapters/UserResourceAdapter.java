package adapters;

import dto.UserDto;
import exceptions.DuplicatedEmailException;
import mappers.UserMapperDto;
import services.UserService;

import javax.inject.Inject;
import java.util.Objects;

public class UserResourceAdapter {

    @Inject private UserService userService;
    @Inject private UserMapperDto mapper;

    public String registerCommand(UserDto dto) throws DuplicatedEmailException {
        var user = mapper.toDomainObject(dto);
        Objects.requireNonNull(user);
        userService.register(user);
        return user.getEmail();
    }
}
