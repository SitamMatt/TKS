package ports.secondary;

import model.User;

public interface UserSearchPort {

    User findByEmail(String email);
}
