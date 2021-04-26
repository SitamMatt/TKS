package ports.secondary

import domain.model.context.rents.Client
import domain.model.context.rents.Product
import domain.model.context.rents.Rent
import domain.model.values.AccessionNumber
import domain.model.values.Email
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
