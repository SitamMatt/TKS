package ports.rent

import core.domain.common.valueobjects.AccessionNumber
import domain.common.valueobjects.Email
import core.domain.rent.Client
import core.domain.rent.Product
import core.domain.rent.Rent
import java.util.*

interface ClientSearchPort {
    fun findByEmail(email: Email): Client?
}

interface ProductSearchPort {
    fun findByAccessionNumber(accessionNumber: core.domain.common.valueobjects.AccessionNumber): Product?
}

interface RentPersistencePort {
    fun save(rent: Rent)
}

interface RentSearchPort {
    fun findActiveByResourceId(accessionNumber: core.domain.common.valueobjects.AccessionNumber): Rent?
    fun getById(id: UUID): Rent?
}

interface IRentRepositoryAdapter : RentPersistencePort, RentSearchPort
interface IClientRepositoryAdapter : ClientSearchPort
interface IProductRepositoryAdapter : ProductSearchPort
