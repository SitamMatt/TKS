package ports.rent

import core.domain.common.valueobjects.AccessionNumber
import core.domain.common.valueobjects.Email
import core.domain.rent.Rent
import java.util.*

interface RentQueryPort {
    @Throws(core.domain.common.exceptions.RentNotFoundException::class)
    fun getDetails(id: UUID): Rent
}

interface ResourceRentCommandPort {
    @Throws(
        core.domain.common.exceptions.UserNotFoundException::class,
        core.domain.common.exceptions.ResourceNotFoundException::class,
        core.domain.common.exceptions.UserNotActiveException::class,
        core.domain.common.exceptions.ResourceAlreadyRentException::class
    )
    fun rent(email: Email, resourceId: AccessionNumber): UUID

    @Throws(
        core.domain.common.exceptions.UserNotFoundException::class,
        core.domain.common.exceptions.ResourceNotFoundException::class,
        core.domain.common.exceptions.ResourceNotRentException::class,
        core.domain.common.exceptions.InvalidUserException::class
    )
    fun returnResource(email: Email, resourceId: AccessionNumber)
}

interface IRentService : RentQueryPort, ResourceRentCommandPort
