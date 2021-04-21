package rest.api.adapters

import domain.exceptions.*
import domain.model.values.AccessionNumber
import domain.model.values.Email
import ports.primary.combined.IRentService
import java.util.*
import javax.inject.Inject
import kotlin.jvm.Throws

class RentResourceAdapter @Inject constructor(
    private val adapter: IRentService
) {

    @Throws(
        UserNotFoundException::class,
        ResourceNotFoundException::class,
        UserNotActiveException::class,
        ResourceAlreadyRentException::class
    )
    fun rent(userId: String, resource: String): UUID {
        val email = Email(userId)
        val accessionNumber = AccessionNumber(resource)
        return adapter.rent(email, accessionNumber);
    }

    @Throws(
        UserNotFoundException::class,
        ResourceNotFoundException::class,
        ResourceNotRentException::class,
        InvalidUserException::class
    )
    fun returnItem(userId: String, resource: String){
        val email = Email(userId)
        val accessionNumber = AccessionNumber(resource)
        adapter.returnResource(email, accessionNumber)
    }
}