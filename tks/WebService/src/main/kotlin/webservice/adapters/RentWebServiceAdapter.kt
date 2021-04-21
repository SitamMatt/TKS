package webservice.adapters

import domain.exceptions.RentNotFoundException
import ports.primary.combined.IRentService
import webservice.dto.RentSoapDto
import webservice.mappers.RentMapper
import java.util.*
import javax.inject.Inject

class RentWebServiceAdapter(
    @Inject private var rentService: IRentService? = null,
    @Inject private var mapper: RentMapper? = null
) {

    @Throws(RentNotFoundException::class)
    fun getRent(id: UUID): RentSoapDto {
        val rent = rentService!!.getDetails(id)
        return mapper!!.toDto(rent)!!
    }
}