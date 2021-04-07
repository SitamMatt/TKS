package ports.secondary;

import domain.model.User;
import domain.model.values.Email;

public interface UserSearchPort {

    User findByEmail(Email email);
}
