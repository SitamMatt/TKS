package ports.secondary

import domain.model.context.rents.Rent

interface RentPersistencePort {
    fun save(rent: Rent)
}