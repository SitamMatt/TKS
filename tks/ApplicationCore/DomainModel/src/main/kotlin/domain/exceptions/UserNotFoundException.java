package domain.exceptions;

import domain.model.Error;

public class UserNotFoundException extends DomainException{
    public UserNotFoundException() {
        super(Error.UserNotFound);
    }
}
