package drivenports;

import model.User;

public interface UserSavePort {

    void add(User user);

    void update(User user);
}
