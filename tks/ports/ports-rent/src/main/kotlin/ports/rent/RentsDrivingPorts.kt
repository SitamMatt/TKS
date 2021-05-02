package ports.rent

import domain.common.exceptions.*
import domain.common.valueobjects.AccessionNumber
import domain.common.valueobjects.Email
import domain.rent.Rent
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
