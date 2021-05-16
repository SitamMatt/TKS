package repository.data

import java.util.*

data class ClientEntity(
    override var guid: UUID?,
    var email: String,
    var active: Boolean,
) : EntityBase()