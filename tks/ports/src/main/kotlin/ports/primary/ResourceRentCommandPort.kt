package ports.primary

import domain.exceptions.*
import domain.model.values.AccessionNumber
import domain.model.values.Email
import java.util.*

interface ResourceRentCommandPort {
    @Throws(
        UserNotFoundException::class,
        ResourceNotFoundException::class,
        UserNotActiveException::class,
        ResourceAlreadyRentException::class
    )
    fun rent(email: Email, resourceId: AccessionNumber): UUID

    @Throws(
        UserNotFoundException::class,
        ResourceNotFoundException::class,
        ResourceNotRentException::class,
        InvalidUserException::class
    )
    fun returnResource(email: Email, resourceId: AccessionNumber)
}