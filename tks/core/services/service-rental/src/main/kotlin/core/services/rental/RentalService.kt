package core.services.rental

import domain.common.exceptions.*
import core.domain.common.valueobjects.AccessionNumber
import domain.common.valueobjects.Email
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
        core.domain.common.exceptions.UserNotFoundException::class,
        core.domain.common.exceptions.ResourceNotFoundException::class,
        core.domain.common.exceptions.UserNotActiveException::class,
        core.domain.common.exceptions.ResourceAlreadyRentException::class
    )
    override fun rent(email: Email, resourceId: core.domain.common.valueobjects.AccessionNumber): UUID {
        val (userEmail, active) = clientSearchPort.findByEmail(email) ?: throw core.domain.common.exceptions.UserNotFoundException()
        productSearchPort.findByAccessionNumber(resourceId) ?: throw core.domain.common.exceptions.ResourceNotFoundException()
        if (!active) throw core.domain.common.exceptions.UserNotActiveException()
        val existingRent = rentSearchPort.findActiveByResourceId(resourceId)
        if (existingRent != null) throw core.domain.common.exceptions.ResourceAlreadyRentException()
        val rent = Rent(UUID.randomUUID(), Date(), null, userEmail, resourceId)
        rentPersistencePort.save(rent)
        return rent.id!!
    }

    @Throws(
        core.domain.common.exceptions.UserNotFoundException::class,
        core.domain.common.exceptions.ResourceNotFoundException::class,
        core.domain.common.exceptions.ResourceNotRentException::class,
        core.domain.common.exceptions.InvalidUserException::class
    )
    override fun returnResource(email: Email, resourceId: core.domain.common.valueobjects.AccessionNumber) {
        val (userEmail) = clientSearchPort.findByEmail(email) ?: throw core.domain.common.exceptions.UserNotFoundException()
        productSearchPort.findByAccessionNumber(resourceId) ?: throw core.domain.common.exceptions.ResourceNotFoundException()
        val rent = rentSearchPort.findActiveByResourceId(resourceId) ?: throw core.domain.common.exceptions.ResourceNotRentException()
        if (rent.userEmail != userEmail) throw core.domain.common.exceptions.InvalidUserException()
        rent.endDate = Date()
        rentPersistencePort.save(rent)
    }

    @Throws(core.domain.common.exceptions.RentNotFoundException::class)
    override fun getDetails(id: UUID): Rent {
        return rentSearchPort.getById(id) ?: throw core.domain.common.exceptions.RentNotFoundException();
    }
}