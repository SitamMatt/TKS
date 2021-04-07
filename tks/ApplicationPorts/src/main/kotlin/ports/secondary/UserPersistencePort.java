package ports.secondary;

import domain.model.User;

public interface UserPersistencePort {

    void add(User user);

    void update(User user);
}
