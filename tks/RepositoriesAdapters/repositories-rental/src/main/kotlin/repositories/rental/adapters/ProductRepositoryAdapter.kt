package repositories.rental.adapters

import core.domain.common.valueobjects.AccessionNumber
import core.domain.rent.Product
import ports.rent.ProductSearchPort
import repositories.rental.entities.ClientEntity
import repositories.rental.entities.ProductEntity
import repositories.rental.mappers.toDomain
import javax.persistence.EntityManager

class ProductRepositoryAdapter(
    private val entityManager: EntityManager
) : ProductSearchPort {

    override fun findByAccessionNumber(accessionNumber: AccessionNumber): Product? {
        return try {
            val query = entityManager.createNamedQuery("ProductEntity.findByAccessionNumber", ProductEntity::class.java)
            query.setParameter("id", accessionNumber.value)
            val entity = query.singleResult
            entity?.toDomain();
        } catch (ex: Exception) {
            null
        }
    }
}