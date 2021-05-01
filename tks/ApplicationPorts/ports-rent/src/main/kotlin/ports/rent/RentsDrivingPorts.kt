package ports.rent

import domain.exceptions.*
import domain.model.context.rents.Rent
import domain.model.values.AccessionNumber
import domain.model.values.Email
import java.util.*

interface RentQueryPort {
    @Throws(RentNotFoundException::class)
    fun getDetails(id: UUID): Rent
}

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

interface IRentService : RentQueryPort, ResourceRentCommandPort
