package domain.exceptions;

import domain.model.ErrorCode;

public class DuplicatedEmailException extends DomainException{
    public DuplicatedEmailException() {
        super(ErrorCode.DuplicatedEmail);
    }
}
