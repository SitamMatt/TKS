package repositories.rental.adapters

import core.domain.common.valueobjects.AccessionNumber
import core.domain.rent.Rent
import ports.rent.IRentRepositoryAdapter
import repositories.rental.mappers.toDomain
import repositories.rental.mappers.toEntity
import repositories.rental.repositories.ClientRepository
import repositories.rental.repositories.ProductRepository
import repositories.rental.repositories.RentRepository
import java.util.*

class RentRepositoryAdapter(
    private val rentRepository: RentRepository,
    private val clientRepository: ClientRepository,
    private val productRepository: ProductRepository
) : IRentRepositoryAdapter {


    override fun findActiveByResourceId(accessionNumber: AccessionNumber): Rent? {
        return try {
            val result = rentRepository.findByProductAccessionNumber(accessionNumber.value)
            if (!result.isPresent) return null
            return result.get().toDomain()
        } catch (ex: Exception) {
            null
        }
    }

    override fun getById(id: UUID): Rent? {
        return try {
            val result = rentRepository.findByIdentifier(id)
            if (!result.isPresent) return null
            return result.get().toDomain()
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
                    rentRepository.save(entity)
                } else {
                    val entity = rentResult.get()
                    rent.toEntity(entity, clientResult.get(), productResult.get())
                    rentRepository.save(entity)
                }
            }
        } catch (ex: Exception) {
            throw ex
        }
    }
}




