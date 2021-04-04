package ports.primary;

import exceptions.UserNotFoundException;
import model.User;

public interface UserQueryPort {

    User getDetails(String email) throws UserNotFoundException;
}
