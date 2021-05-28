package repositories.rental.repositories

import org.apache.deltaspike.data.api.EntityRepository
import org.apache.deltaspike.data.api.Query
import org.apache.deltaspike.data.api.Repository
import org.apache.deltaspike.jpa.api.transaction.Transactional
import repositories.rental.entities.RentEntity
import java.util.*

@Transactional
@Repository(forEntity = RentEntity::class)
interface RentRepository : EntityRepository<RentEntity, Long> {

    fun findByIdentifier(identifier: UUID): Optional<RentEntity>

    @Query(named = "RentEntity.findByProductAccessionNumber")
    fun findByProductAccessionNumber(accessionNumber: String) : Optional<RentEntity>
}