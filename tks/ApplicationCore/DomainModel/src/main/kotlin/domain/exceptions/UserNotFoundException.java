package domain.exceptions;

import domain.model.ErrorCode;

public class UserNotFoundException extends DomainException{
    public UserNotFoundException() {
        super(ErrorCode.UserNotFound);
    }
}
