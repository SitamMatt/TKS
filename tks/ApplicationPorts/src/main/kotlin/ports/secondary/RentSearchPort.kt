package ports.secondary

import domain.model.Rent
import domain.model.values.AccessionNumber

interface RentSearchPort {
    fun findActiveByResourceId(accessionNumber: AccessionNumber): Rent?
}