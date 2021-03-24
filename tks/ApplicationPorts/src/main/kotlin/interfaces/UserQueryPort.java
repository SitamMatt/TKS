package interfaces;

import model.User;

public interface UserQueryPort {

    User findByEmail(String email);
}
