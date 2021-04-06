package adapters

import exceptions.*
import model.values.AccessionNumber
import model.values.Email
import ports.primary.ResourceRentCommandPort
import javax.inject.Inject
import kotlin.jvm.Throws

class RentResourceAdapter {

    @Inject
    private lateinit var adapter: ResourceRentCommandPort

    @Throws(
        UserNotFoundException::class,
        ResourceNotFoundException::class,
        UserNotActiveException::class,
        ResourceAlreadyRentException::class
    )
    fun rent(userId: String, resource: String){
        val email = Email(userId)
        val accessionNumber = AccessionNumber(resource)
        adapter.rent(email, accessionNumber)
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