package repositories.rental.adapters

import core.domain.common.valueobjects.AccessionNumber
import core.domain.rent.Product
import ports.rent.ProductSearchPort
import repositories.rental.entities.ProductEntity
import repositories.rental.mappers.toDomain
import repositories.rental.mappers.toEntity
import repositories.rental.repositories.ProductRepository
import javax.persistence.EntityManager

class ProductRepositoryAdapter(
    private val entityManager: EntityManager,
    private val productRepository: ProductRepository
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

    fun save(product: Product) {
        try{
            entityManager.transaction.begin()
            val result = productRepository.findByAccessionNumber(product.accessionNumber!!.value)
            if(result.isEmpty){
                val entity = product.toEntity()
                productRepository.save(entity)
            }else{
                val entity = result.get()
                product.toEntity(entity)
                productRepository.save(entity)
            }
            entityManager.transaction.commit()
            entityManager.clear()
        }catch(ex: Exception){
            throw ex
        }
    }

    fun getProductEntityByAccessionNumber(accessionNumber: AccessionNumber): ProductEntity? {
        return try {
            val query = entityManager.createNamedQuery("ProductEntity.findByAccessionNumber", ProductEntity::class.java)
            query.setParameter("id", accessionNumber.value)
            val entity = query.singleResult
            entity
        } catch (ex: Exception) {
            null
        }
    }
}