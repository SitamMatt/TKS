package ports.secondary

import domain.model.traits.Resource
import domain.model.values.AccessionNumber

interface ResourceSearchPort {
    fun findByAccessionNumber(accessionNumber: AccessionNumber): Resource?
}