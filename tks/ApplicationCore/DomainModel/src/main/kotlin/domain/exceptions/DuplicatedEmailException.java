package domain.exceptions;

import domain.model.Error;

public class DuplicatedEmailException extends DomainException{
    public DuplicatedEmailException() {
        super(Error.DuplicatedEmail);
    }
}
