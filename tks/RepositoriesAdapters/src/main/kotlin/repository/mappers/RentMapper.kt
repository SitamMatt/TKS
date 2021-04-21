package repository.mappers

import domain.model.Rent
import domain.model.values.AccessionNumber
import domain.model.values.Email
import repository.data.RentEntity

class RentMapper {

    fun mapEntityToDomainObject(entity: RentEntity?): Rent? = if (entity == null) null else Rent(
        entity.id,
        entity.startDate,
        entity.endDate,
        Email(entity.user!!.email),
        AccessionNumber(entity.resource!!.accessionNumber!!)
    )

    fun mapDomainObjectToEntity(user: Rent?): RentEntity? = if (user == null) null else RentEntity(
        null,
        user.id,
        user.startDate,
        user.endDate,
        null,
        null
    )

    fun mapDomainObjectToEntity(user: Rent?, entity: RentEntity) {
        user ?: return
        entity.apply {
            id = user.id
            startDate = user.startDate
            endDate = user.endDate
        }
    }

    companion object {
        val INSTANCE = RentMapper()
    }
}