package ports.rent

import domain.common.valueobjects.AccessionNumber
import domain.common.valueobjects.Email
import domain.rent.Client
import domain.rent.Product
import domain.rent.Rent
import java.util.*

interface ClientSearchPort {
    fun findByEmail(email: Email): Client?
}

interface ProductSearchPort {
    fun findByAccessionNumber(accessionNumber: AccessionNumber): Product?
}

interface RentPersistencePort {
    fun save(rent: Rent)
}

interface RentSearchPort {
    fun findActiveByResourceId(accessionNumber: AccessionNumber): Rent?
    fun getById(id: UUID): Rent?
}

interface IRentRepositoryAdapter : RentPersistencePort, RentSearchPort
interface IClientRepositoryAdapter : ClientSearchPort
interface IProductRepositoryAdapter : ProductSearchPort
