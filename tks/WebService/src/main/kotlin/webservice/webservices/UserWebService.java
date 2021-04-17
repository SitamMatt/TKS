package webservice.webservices;

import domain.exceptions.TypeValidationFailedException;
import domain.exceptions.UserNotFoundException;
import webservice.adapters.UserWebServiceAdapter;
import webservice.dto.UserSoapDto;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class UserWebService {

    @Inject
    private UserWebServiceAdapter serviceAdapter;

    @WebMethod
    public UserSoapDto getUser(String email) throws UserNotFoundException, TypeValidationFailedException {
        try{
            return serviceAdapter.getUser(email);
        } catch (UserNotFoundException | TypeValidationFailedException e) {
            throw e;
        }
    }
}
