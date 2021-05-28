package repositories.rental.repositories

import org.apache.deltaspike.data.api.EntityRepository
import org.apache.deltaspike.data.api.Repository
import org.apache.deltaspike.jpa.api.transaction.Transactional
import repositories.rental.entities.ProductEntity
import java.util.*

@Transactional
@Repository(forEntity = ProductEntity::class)
interface ProductRepository : EntityRepository<ProductEntity, Long> {


    fun findByAccessionNumber(accessionNumber: String): Optional<ProductEntity>
}