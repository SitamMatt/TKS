package domain.exceptions;

import domain.model.Error;

public class DomainException extends Exception{
    private final Error error;

    public DomainException(Error error) {
        this.error = error;
    }

    public Error getErrors() {
        return error;
    }
}
