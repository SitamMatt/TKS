package data

import java.util.*

data class RentEntity(
    var id: UUID?,
    val startDate: Date,
    var endDate: Date?,
    var user: UserEntity,
    var resource: AbstractResourceEntity
) : EntityBase();
