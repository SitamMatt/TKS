package rest.api.adapters;

import rest.api.dto.UserDto;
import domain.exceptions.DuplicatedEmailException;
import domain.exceptions.TypeValidationFailedException;
import domain.exceptions.UserNotFoundException;
import rest.api.mappers.UserMapperDto;
import domain.model.values.Email;
import ports.primary.combined.IUserService;

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
