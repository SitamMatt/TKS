package domain.exceptions;

import domain.model.ErrorCode;

public class DomainException extends Exception{
    private final ErrorCode errorCode;

    public DomainException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getError() {
        return errorCode;
    }
}
