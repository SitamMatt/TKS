package rest.api.mappers

import domain.model.Rent
import rest.api.dto.RentDto

class RentMapperDto {

    fun toDto(src: Rent?): RentDto? = if (src == null) null else RentDto().apply {
        id = src.id
        startDate = src.startDate
        endDate = src.endDate
    }
}