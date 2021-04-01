package drivenports;

import model.User;

public interface UserQueryPort {

    User findByEmail(String email);
}
