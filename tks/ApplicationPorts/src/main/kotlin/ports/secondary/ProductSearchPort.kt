package ports.secondary

import domain.model.context.rents.Product
import domain.model.traits.Resource
import domain.model.values.AccessionNumber

interface ProductSearchPort {
    fun findByAccessionNumber(accessionNumber: AccessionNumber): Product?

}