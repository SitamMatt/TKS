package core.services.rental

import core.domain.common.exceptions.ResourceAlreadyRentException
import core.domain.common.exceptions.ResourceNotFoundException
import core.domain.common.exceptions.UserNotActiveException
import core.domain.common.exceptions.UserNotFoundException
import core.domain.common.valueobjects.AccessionNumber
import core.domain.common.valueobjects.Email
import core.domain.rent.Rent
import ports.rent.*
import java.util.*

open class RentalService(
    private val rentPersistencePort: RentPersistencePort,
    private val rentSearchPort: RentSearchPort,
    private val clientSearchPort: ClientSearchPort,
    private val productSearchPort: ProductSearchPort
) : IRentService {

    @Throws(
        UserNotFoundException::class,
        ResourceNotFoundException::class,
        UserNotActiveException::class,
        ResourceAlreadyRentException::class
    )
    override fun rent(email: Email, resourceId: AccessionNumber): UUID {
        val (userEmail, active) = clientSearchPort.findByEmail(email)
            ?: throw UserNotFoundException()
        productSearchPort.findByAccessionNumber(resourceId)
            ?: throw ResourceNotFoundException()
        if (!active) throw UserNotActiveException()
        val existingRent = rentSearchPort.findActiveByResourceId(resourceId)
        if (existingRent != null) throw ResourceAlreadyRentException()
        val rent = Rent(UUID.randomUUID(), Date(), null, userEmail, resourceId)
        rentPersistencePort.save(rent)
        return rent.id!!
    }

    @Throws(
        UserNotFoundException::class,
        ResourceNotFoundException::class,
        core.domain.common.exceptions.ResourceNotRentException::class,
        core.domain.common.exceptions.InvalidUserException::class
    )
    override fun returnResource(email: Email, resourceId: AccessionNumber) {
        val (userEmail) = clientSearchPort.findByEmail(email)
            ?: throw UserNotFoundException()
        productSearchPort.findByAccessionNumber(resourceId)
            ?: throw ResourceNotFoundException()
        val rent = rentSearchPort.findActiveByResourceId(resourceId)
            ?: throw core.domain.common.exceptions.ResourceNotRentException()
        if (rent.userEmail != userEmail) throw core.domain.common.exceptions.InvalidUserException()
        rent.endDate = Date()
        rentPersistencePort.save(rent)
    }

    @Throws(core.domain.common.exceptions.RentNotFoundException::class)
    override fun getDetails(id: UUID): Rent {
        return rentSearchPort.getById(id) ?: throw core.domain.common.exceptions.RentNotFoundException()
    }
}