package repositories.rental.adapters

import core.domain.common.valueobjects.AccessionNumber
import core.domain.common.valueobjects.Email
import core.domain.rent.Rent
import ports.rent.RentPersistencePort
import ports.rent.RentSearchPort
import repositories.rental.entities.ClientEntity
import repositories.rental.entities.ProductEntity
import repositories.rental.entities.RentEntity
import repositories.rental.mappers.toDomain
import repositories.rental.mappers.toEntity
import java.util.*
import javax.persistence.EntityManager
import javax.transaction.Transactional

class RentRepositoryAdapter(val entityManager: EntityManager) : RentSearchPort, RentPersistencePort {


    override fun findActiveByResourceId(accessionNumber: AccessionNumber): Rent? {
        return try {
            val query = entityManager.createNamedQuery("RentEntity.findByProductAccessionNumber", RentEntity::class.java)
            query.setParameter("id", accessionNumber.value)
            val entity = query.singleResult
            entity?.toDomain()
        } catch (ex: Exception) {
            null
        }
    }

    override fun getById(id: UUID): Rent? {
        return try {
            val query = entityManager.createNamedQuery("RentEntity.findByIdentifier", RentEntity::class.java)
            query.setParameter("id", id)
            val entity = query.singleResult
            entity?.toDomain();
        } catch (ex: Exception) {
            null
        }
    }

    override fun save(rent: Rent) {
        try{
            entityManager.transaction.begin()
            val entity = getRentEntityById(rent.id!!)
            val client = getClientEntityByEmail(rent.userEmail)!!
            val product = getProductEntityByAccessionNumber(rent.resourceId)!!
            if(entity == null){
                val newEntity = rent.toEntity(client, product)
                entityManager.persist(newEntity)
            }else{
                rent.toEntity(entity, client, product)
                entityManager.merge(entity)
            }
            entityManager.transaction.commit()
            entityManager.clear()
        }catch(ex: Exception){
            throw ex
        }
    }

    fun getClientEntityByEmail(email: Email): ClientEntity? {
        return try {
            val query = entityManager.createNamedQuery("ClientEntity.findByEmail", ClientEntity::class.java)
            query.setParameter("email", email.value)
            val entity = query.singleResult
            entity
        } catch (ex: Exception) {
            null
        }
    }

    fun getRentEntityById(id: UUID): RentEntity? {
        return try {
            val query = entityManager.createNamedQuery("RentEntity.findByIdentifier", RentEntity::class.java)
            query.setParameter("id", id)
            val entity = query.singleResult
            entity
        } catch (ex: Exception) {
            null
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




