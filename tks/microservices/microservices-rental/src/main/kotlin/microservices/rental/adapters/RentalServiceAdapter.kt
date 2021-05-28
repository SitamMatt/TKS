package microservices.rental.adapters

import core.domain.common.exceptions.DomainException
import core.domain.common.exceptions.InvalidUserException
import core.domain.common.exceptions.RentNotFoundException
import core.domain.common.exceptions.ResourceAlreadyRentException
import core.domain.common.valueobjects.AccessionNumber
import core.domain.common.valueobjects.Email
import microservices.common.error.Status
import microservices.rental.dto.RentDto
import microservices.common.error.ConflictException
import microservices.common.error.ResourceNotFoundException
import microservices.rental.mappers.toDto
import ports.rent.IRentalService
import java.util.*
import javax.inject.Inject
import javax.ws.rs.core.Context
import javax.ws.rs.core.SecurityContext

open class RentalServiceAdapter @Inject constructor(
    private val rentalService: IRentalService
) {
    // todo remove...
    private val userEmail: String = "mszewc@edu.pl"


    @Context
    private lateinit var securityContext: SecurityContext

    open fun queryRent(guid: UUID): Result<RentDto> {
        val rent = rentalService.getDetails(guid)
            ?: return Result.failure(ResourceNotFoundException())
        return Result.success(rent.toDto())
    }

    open fun createRentByUser(productId: String): Result<RentDto> {
        try {
            val email = Email(userEmail)
            val accessionNumber = AccessionNumber(productId)
            val rent = rentalService.rent(email, accessionNumber)
            return Result.success(rent.toDto())
        } catch (e: RentNotFoundException) {
            return Result.failure(ResourceNotFoundException())
        } catch (e: ResourceAlreadyRentException) {
            return Result.failure(ConflictException(Status.ProductLocked))
        } catch (e: DomainException) {
            return Result.failure(e)
        }
    }

    open fun finalizeRentByUser(id: UUID): Result<RentDto> {
        try {
            val email = Email(userEmail)
            val rent = rentalService.returnResource(email, id)
            return Result.success(rent.toDto())
        } catch (e: RentNotFoundException) {
            return Result.failure(ResourceNotFoundException())
        } catch (e: InvalidUserException) {
            return Result.failure(ResourceNotFoundException())
        } catch (e: DomainException) {
            return Result.failure(e)
        }
    }
}
////    private val adapter: IRentService,
////    private val mapper: RentMapperDto
//) {
//
////    @Throws(
////        UserNotFoundException::class,
////        ResourceNotFoundException::class,
////        UserNotActiveException::class,
////        ResourceAlreadyRentException::class,
////        TypeValidationFailedException::class
////    )
//
////    fun find()
////    fun rent(userId: String, resource: String): UUID {
////        val email = Email(userId)
////        val accessionNumber = AccessionNumber(resource)
//////        return adapter.rent(email, accessionNumber);
////        return UUID.randomUUID()
////    }
////
////    @Throws(
////        UserNotFoundException::class,
////        ResourceNotFoundException::class,
////        ResourceNotRentException::class,
////        InvalidUserException::class
////    )
////    fun returnItem(userId: String, resource: String){
////        val email = Email(userId)
////        val accessionNumber = AccessionNumber(resource)
//////        adapter.returnResource(email, accessionNumber)
////    }
////
////    @Throws(RentNotFoundException::class)
////    fun queryCommand(rentId: String): RentDto {
////        val guid = UUID.fromString(rentId)
//////        val rent = adapter.getDetails(guid)
//////        return mapper.toDto(rent)!!
////        return RentDto(null,null, null,null, null)
////    }
//}