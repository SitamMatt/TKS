package ports.primary

import domain.exceptions.ResourceNotFoundException
import domain.model.context.library.Resource
import domain.model.values.AccessionNumber

interface ResourceQueryPort {
    @Throws(ResourceNotFoundException::class)
    fun getDetails(accessionNumber: AccessionNumber): Resource?
}