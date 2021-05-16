package repository.data

import java.util.*

class RentEntity(
    override var guid: UUID?,
    var id: UUID?,
    var startDate: Date,
    var endDate: Date?,
    var user: ClientEntity?,
    var resource: ProductEntity?,
) : EntityBase();
