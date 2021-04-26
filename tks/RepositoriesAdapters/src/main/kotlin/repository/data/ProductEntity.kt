package repository.data

import java.util.*

data class ProductEntity(
    override var guid: UUID?,
    var accessionNumber: String?,
) : EntityBase();