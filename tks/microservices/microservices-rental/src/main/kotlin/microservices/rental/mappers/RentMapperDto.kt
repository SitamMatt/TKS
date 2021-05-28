package microservices.rental.mappers

import core.domain.rent.Rent
import microservices.rental.dto.RentDto

fun Rent.toDto(): RentDto {
    return RentDto(
        this.id!!,
        this.startDate,
        this.endDate,
        this.resourceId.value,
        this.userEmail.value
    )
}