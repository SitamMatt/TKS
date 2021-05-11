package repositories.library.repositories

import io.micronaut.context.annotation.Executable
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import repositories.library.entities.ResourceEntityTrait

@Repository
interface ResourceRepository : CrudRepository<ResourceEntityTrait, Long> {
    @Executable
    fun find(accessionNumber: String): ResourceEntityTrait
}