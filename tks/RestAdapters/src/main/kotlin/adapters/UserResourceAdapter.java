package adapters;

import dto.UserDto;
import exceptions.DuplicatedEmailException;
import exceptions.TypeValidationFailedException;
import exceptions.UserNotFoundException;
import mappers.UserMapperDto;
import model.values.Email;
import ports.primary.IUserService;

import javax.inject.Inject;
import java.util.Objects;

public class UserResourceAdapter {

    @Inject
    private IUserService userService;
    @Inject
    private UserMapperDto mapper;

    public String registerCommand(UserDto dto) throws DuplicatedEmailException {
        var user = mapper.toDomainObject(dto);
        Objects.requireNonNull(user);
        userService.register(user);
        return user.getEmail().getValue();
    }

    public UserDto queryUser(String email) throws TypeValidationFailedException, UserNotFoundException {
        var emailObject = new Email(email);
        var user = userService.getDetails(emailObject);
        return mapper.toDto(user);
    }
}
