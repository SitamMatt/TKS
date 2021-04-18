package ports.secondary

import domain.model.Rent

interface RentPersistencePort {
    fun save(rent: Rent)
}