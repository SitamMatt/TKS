package ports.primary;

import exceptions.*;
import model.values.AccessionNumber;
import model.values.Email;

public interface ResourceRentCommandPort {

    void rent(Email email, AccessionNumber resourceId) throws UserNotFoundException, ResourceNotFoundException, UserNotActiveException, ResourceAlreadyRentException;
    void returnResource(Email email, AccessionNumber resourceId) throws UserNotFoundException, ResourceNotFoundException, ResourceNotRentException, InvalidUserException;
}
