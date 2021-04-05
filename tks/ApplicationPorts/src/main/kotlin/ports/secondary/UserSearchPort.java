package ports.secondary;

import model.User;
import model.values.Email;

public interface UserSearchPort {

    User findByEmail(Email email);
}
