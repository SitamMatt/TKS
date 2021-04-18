package ports.primary;

import domain.exceptions.UserNotFoundException;
import domain.model.User;
import domain.model.values.Email;

public interface UserQueryPort {

    User getDetails(Email email) throws UserNotFoundException;
}
