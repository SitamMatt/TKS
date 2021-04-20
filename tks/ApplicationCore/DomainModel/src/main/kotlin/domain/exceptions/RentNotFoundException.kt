package domain.exceptions

import domain.model.ErrorCode

class RentNotFoundException : DomainException(ErrorCode.RentDoesNotExist) {
}