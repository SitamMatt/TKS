package repositories.library.repositories

import io.micronaut.context.annotation.Executable
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import repositories.library.entities.ResourceEntityTrait
import java.util.*

@Repository
interface ResourceRepository : CrudRepository<ResourceEntityTrait, Long> {
    @Executable
    fun find(accessionNumber: String): Optional<ResourceEntityTrait>
}