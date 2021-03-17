package interfaces;

import model.User;

public interface UserFilterPort {

    User findByEmail(String email);
}
