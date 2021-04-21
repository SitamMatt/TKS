package application.services

import domain.exceptions.*
import domain.model.Rent
import domain.model.values.AccessionNumber
import domain.model.values.Email
import ports.primary.combined.IRentService
import ports.secondary.RentPersistencePort
import ports.secondary.RentSearchPort
import ports.secondary.ResourceSearchPort
import ports.secondary.UserSearchPort
import java.util.*

open class RentService(
    private val rentPersistencePort: RentPersistencePort,
    private val rentSearchPort: RentSearchPort,
    private val userSearchPort: UserSearchPort,
    private val resourceSearchPort: ResourceSearchPort
) : IRentService {

    @Throws(
        UserNotFoundException::class,
        ResourceNotFoundException::class,
        UserNotActiveException::class,
        ResourceAlreadyRentException::class
    )
    override fun rent(email: Email, resourceId: AccessionNumber): UUID {
        val (userEmail, _, _, active) = userSearchPort.findByEmail(email) ?: throw UserNotFoundException()
        resourceSearchPort.findByAccessionNumber(resourceId) ?: throw ResourceNotFoundException()
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
        ResourceNotRentException::class,
        InvalidUserException::class
    )
    override fun returnResource(email: Email, resourceId: AccessionNumber) {
        val (userEmail) = userSearchPort.findByEmail(email) ?: throw UserNotFoundException()
        resourceSearchPort.findByAccessionNumber(resourceId) ?: throw ResourceNotFoundException()
        val rent = rentSearchPort.findActiveByResourceId(resourceId) ?: throw ResourceNotRentException()
        if (rent.userEmail != userEmail) throw InvalidUserException()
        rent.endDate = Date()
        rentPersistencePort.save(rent)
    }

    @Throws(RentNotFoundException::class)
    override fun getDetails(id: UUID): Rent {
        return rentSearchPort.getById(id) ?: throw RentNotFoundException();
    }
}