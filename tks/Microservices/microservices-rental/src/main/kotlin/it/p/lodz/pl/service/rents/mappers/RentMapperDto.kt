package it.p.lodz.pl.service.rents.mappers

import core.domain.rent.Rent
import it.p.lodz.pl.service.rents.dto.RentDto

class RentMapperDto {

    fun toDto(src: Rent?): RentDto? = if (src == null) null else RentDto().apply {
        id = src.id
        startDate = src.startDate
        endDate = src.endDate
        resourceAccessionNumber = src.resourceId.value
        userEmail = src.userEmail.value
    }
}