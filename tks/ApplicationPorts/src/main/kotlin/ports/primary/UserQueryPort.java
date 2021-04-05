package ports.primary;

import exceptions.UserNotFoundException;
import model.User;
import model.values.Email;

public interface UserQueryPort {

    User getDetails(Email email) throws UserNotFoundException;
}
