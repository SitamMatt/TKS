package ports.primary

import domain.exceptions.UnknownResourceException
import domain.model.context.library.Resource
import domain.model.values.AccessionNumber

interface ResourceManageCommandPort {
    @Throws(UnknownResourceException::class)
    fun create(resource: Resource)
    fun update(resource: Resource)
    fun remove(accessionNumber: AccessionNumber)
}