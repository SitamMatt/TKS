package domain.exceptions

import domain.model.ErrorCode

class DuplicatedEmailException : DomainException(ErrorCode.DuplicatedEmail)