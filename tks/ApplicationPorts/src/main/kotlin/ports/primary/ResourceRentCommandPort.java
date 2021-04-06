package ports.primary;

import domain.exceptions.*;
import domain.model.values.AccessionNumber;
import domain.model.values.Email;

public interface ResourceRentCommandPort {

    void rent(Email email, AccessionNumber resourceId) throws UserNotFoundException, ResourceNotFoundException, UserNotActiveException, ResourceAlreadyRentException;
    void returnResource(Email email, AccessionNumber resourceId) throws UserNotFoundException, ResourceNotFoundException, ResourceNotRentException, InvalidUserException;
}
