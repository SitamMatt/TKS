package it.p.lodz.pl.service.rents.adapters

import domain.exceptions.*
import domain.model.values.AccessionNumber
import domain.model.values.Email
import it.p.lodz.pl.service.rents.dto.RentDto
import it.p.lodz.pl.service.rents.mappers.RentMapperDto
import ports.primary.combined.IRentService
import java.util.*
import javax.inject.Inject
import kotlin.jvm.Throws

class RentResourceAdapter @Inject constructor(
    private val adapter: IRentService,
    private val mapper: RentMapperDto
) {

    @Throws(
        UserNotFoundException::class,
        ResourceNotFoundException::class,
        UserNotActiveException::class,
        ResourceAlreadyRentException::class,
        TypeValidationFailedException::class
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

    @Throws(RentNotFoundException::class)
    fun queryCommand(rentId: String): RentDto {
        val guid = UUID.fromString(rentId)
        val rent = adapter.getDetails(guid)
        return mapper.toDto(rent)!!
    }
}