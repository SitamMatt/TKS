package core.services.rental

import core.domain.common.exceptions.*
import core.domain.common.valueobjects.AccessionNumber
import core.domain.common.valueobjects.Email
import core.domain.rent.Rent
import ports.rent.*
import java.util.*

open class RentalService(
    private val rentPersistencePort: RentPersistencePort?,
    private val rentSearchPort: RentSearchPort?,
    private val clientSearchPort: ClientSearchPort?,
    private val productSearchPort: ProductSearchPort?
) : IRentalService {

    @Throws(
        UserNotFoundException::class,
        ResourceNotFoundException::class,
        UserNotActiveException::class,
        ResourceAlreadyRentException::class
    )
    override fun rent(email: Email, resourceId: AccessionNumber): Rent {
        val (userEmail, active) = clientSearchPort!!.findByEmail(email)
            ?: throw UserNotFoundException()
        productSearchPort!!.findByAccessionNumber(resourceId)
            ?: throw ResourceNotFoundException()
        if (!active) throw UserNotActiveException()
        val existingRent = rentSearchPort!!.findActiveByResourceId(resourceId)
        if (existingRent != null) throw ResourceAlreadyRentException()
        val rent = Rent(UUID.randomUUID(), Date(), null, userEmail, resourceId)
        rentPersistencePort!!.save(rent)
        return rent
    }

    @Throws(
        UserNotFoundException::class,
        ResourceNotFoundException::class,
        ResourceNotRentException::class,
        InvalidUserException::class
    )
    override fun returnResource(email: Email, rentId: UUID): Rent {
        val (userEmail) = clientSearchPort!!.findByEmail(email)
            ?: throw UserNotFoundException()
        val rent = rentSearchPort!!.getById(rentId)
            ?: throw RentNotFoundException()
        if (rent.userEmail != userEmail) throw InvalidUserException()
        rent.endDate = Date()
        rentPersistencePort!!.save(rent)
        return rent
    }

    override fun getDetails(id: UUID): Rent? {
        return rentSearchPort!!.getById(id)
    }
}