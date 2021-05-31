package repositories.rental.adapters

import core.domain.common.valueobjects.AccessionNumber
import core.domain.rent.Rent
import ports.rent.IRentRepositoryAdapter
import ports.rent.RentPersistencePort
import ports.rent.RentSearchPort
import repositories.rental.mappers.toDomain
import repositories.rental.mappers.toEntity
import repositories.rental.repositories.ClientRepository
import repositories.rental.repositories.ProductRepository
import repositories.rental.repositories.RentRepository
import java.util.*
import javax.persistence.EntityManager

class RentRepositoryAdapter(
    val entityManager: EntityManager,
    private val rentRepository: RentRepository,
    private val clientRepository: ClientRepository,
    private val productRepository: ProductRepository
) : IRentRepositoryAdapter {


    override fun findActiveByResourceId(accessionNumber: AccessionNumber): Rent? {
        return try {
            val result = rentRepository.findByProductAccessionNumber(accessionNumber.value)
            if (!result.isPresent) return null
            return result.get().toDomain()
//            val query = entityManager.createNamedQuery("RentEntity.findByProductAccessionNumber", RentEntity::class.java)
//            query.setParameter("id", accessionNumber.value)
//            val entity = query.singleResult
//            entity?.toDomain()
        } catch (ex: Exception) {
            null
        }
    }

    override fun getById(id: UUID): Rent? {
        return try {
            val result = rentRepository.findByIdentifier(id)
            if (!result.isPresent) return null
            return result.get().toDomain()
//            val query = entityManager.createNamedQuery("RentEntity.findByIdentifier", RentEntity::class.java)
//            query.setParameter("id", id)
//            val entity = query.singleResult
//            entity?.toDomain();
        } catch (ex: Exception) {
            null
        }
    }

    override fun save(rent: Rent) {
        try {
            val rentResult = rentRepository.findByIdentifier(rent.id!!)
            val clientResult = clientRepository.findByEmail(rent.userEmail.value)
            val productResult = productRepository.findByAccessionNumber(rent.resourceId.value)
            if (clientResult.isPresent and productResult.isPresent) {
                if (!rentResult.isPresent) {
                    val entity = rent.toEntity(clientResult.get(), productResult.get())
                    entityManager.persist(entity)
                } else {
                    val entity = rentResult.get()
                    rent.toEntity(entity, clientResult.get(), productResult.get())
                    entityManager.merge(rentResult)
                }
            }
//            entityManager.transaction.begin()
//            val entity = getRentEntityById(rent.id!!)
//            val client = getClientEntityByEmail(rent.userEmail)!!
//            val product = getProductEntityByAccessionNumber(rent.resourceId)!!
//            if(entity == null){
//                val newEntity = rent.toEntity(client, product)
//                entityManager.persist(newEntity)
//            }else{
//                rent.toEntity(entity, client, product)
//                entityManager.merge(entity)
//            }
//            entityManager.transaction.commit()
//            entityManager.clear()
        } catch (ex: Exception) {
            throw ex
        }
    }

//    fun getClientEntityByEmail(email: Email): ClientEntity? {
//        return try {
//            val query = entityManager.createNamedQuery("ClientEntity.findByEmail", ClientEntity::class.java)
//            query.setParameter("email", email.value)
//            val entity = query.singleResult
//            entity
//        } catch (ex: Exception) {
//            null
//        }
//    }
//
//    fun getRentEntityById(id: UUID): RentEntity? {
//        return try {
//            val query = entityManager.createNamedQuery("RentEntity.findByIdentifier", RentEntity::class.java)
//            query.setParameter("id", id)
//            val entity = query.singleResult
//            entity
//        } catch (ex: Exception) {
//            null
//        }
//    }

//    fun getProductEntityByAccessionNumber(accessionNumber: AccessionNumber): ProductEntity? {
//        return try {
//            val query = entityManager.createNamedQuery("ProductEntity.findByAccessionNumber", ProductEntity::class.java)
//            query.setParameter("id", accessionNumber.value)
//            val entity = query.singleResult
//            entity
//        } catch (ex: Exception) {
//            null
//        }
//    }
}




