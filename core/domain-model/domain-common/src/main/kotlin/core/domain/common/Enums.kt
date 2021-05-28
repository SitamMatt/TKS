package core.domain.common

enum class UserRole{
    ADMIN, WORKER, CLIENT
}

enum class ResourceType{
    BOOK, MAGAZINE
}

enum class ErrorCode(val message: String, val code: Int) {
    UserNotFound("User not found", 1),
    ResourceNotFound("Resource not found", 2),
    RentDoesNotExist("Rent does not exist", 3),

    ResourceRented("Resource already rented", 11),
    InactiveUser("User is inactive", 12),
    DuplicatedEmail("Email is taken", 13),
    InvalidResourceFormat("The resource format is invalid", 14),
    ResourceInUse("Resource is blocked by active rental", 15),
    InvalidIdentifierFormat("The given identifier format is invalid", 16)
}