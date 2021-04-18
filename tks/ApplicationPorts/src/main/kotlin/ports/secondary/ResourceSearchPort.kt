package ports.secondary

import domain.model.traits.Resource
import domain.model.values.AccessionNumber

interface ResourceSearchPort {
    fun findById(accessionNumber: AccessionNumber): Resource?
}