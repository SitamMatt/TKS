package ports.primary

import domain.exceptions.*
import domain.model.values.AccessionNumber
import domain.model.values.Email

interface ResourceRentCommandPort {
    @Throws(
        UserNotFoundException::class,
        ResourceNotFoundException::class,
        UserNotActiveException::class,
        ResourceAlreadyRentException::class
    )
    fun rent(email: Email, resourceId: AccessionNumber)

    @Throws(
        UserNotFoundException::class,
        ResourceNotFoundException::class,
        ResourceNotRentException::class,
        InvalidUserException::class
    )
    fun returnResource(email: Email, resourceId: AccessionNumber)
}