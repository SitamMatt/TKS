package webservice.adapters;

import domain.exceptions.TypeValidationFailedException;
import domain.exceptions.UserNotFoundException;
import domain.model.values.Email;
import application.services.UserService;
import webservice.dto.UserSoapDto;
import webservice.mappers.IUserMapper;

import javax.inject.Inject;

public class UserWebServiceAdapter {

    @Inject
    private UserService userService;
    @Inject
    private IUserMapper mapper;

    public UserSoapDto getUser(String email) throws TypeValidationFailedException, UserNotFoundException {
        var key = new Email(email);
        var user = userService.getDetails(key);
        return mapper.toDto(user);
    }
}
