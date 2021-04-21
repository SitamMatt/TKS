package ports.secondary

import domain.model.Rent
import domain.model.values.AccessionNumber
import java.util.*

interface RentSearchPort {
    fun findActiveByResourceId(accessionNumber: AccessionNumber): Rent?
    fun getById(id: UUID): Rent?
}