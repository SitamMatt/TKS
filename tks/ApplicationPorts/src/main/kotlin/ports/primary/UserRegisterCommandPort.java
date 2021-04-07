package ports.primary;

import domain.exceptions.DuplicatedEmailException;
import domain.model.User;

public interface UserRegisterCommandPort {

    void register(User user) throws DuplicatedEmailException;
}
