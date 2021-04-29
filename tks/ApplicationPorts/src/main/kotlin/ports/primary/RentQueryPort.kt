package ports.primary

import domain.exceptions.RentNotFoundException
import domain.model.context.rents.Rent
import java.util.*

interface RentQueryPort {
    @Throws(RentNotFoundException::class)
    fun getDetails(id: UUID): Rent
}