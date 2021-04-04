package ports.secondary;

import model.User;

public interface UserPersistencePort {

    void add(User user);

    void update(User user);
}
