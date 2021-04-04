package ports.primary;

import exceptions.DuplicatedEmailException;
import model.User;

public interface UserRegisterCommandPort {

    void register(User user) throws DuplicatedEmailException;
}
