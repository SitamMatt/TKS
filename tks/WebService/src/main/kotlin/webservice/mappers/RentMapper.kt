package webservice.mappers

import domain.model.Rent
import webservice.dto.RentSoapDto

class RentMapper {

    fun toDto(src: Rent?): RentSoapDto? = if(src == null) null else RentSoapDto().apply {
        id = src.id
        startDate = src.startDate
        endDate = src.endDate
        resourceAccessionNumber = src.resourceId.value
        userEmail = src.userEmail.value
    }

    companion object{
        val INSTANCE: RentMapper = RentMapper()
    }
}