package interfaces;

import model.User;

public interface UserSavePort {

    void add(User user);

    void update(User user);
}
