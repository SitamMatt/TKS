package repository.data

import java.util.*

data class UserEntity(
    override var guid: UUID?,
    var email: String,
    var role: String,
    var password: String,
    var active: Boolean,
) : EntityBase()
