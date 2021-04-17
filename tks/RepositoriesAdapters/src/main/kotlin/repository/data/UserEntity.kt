package repository.data

class UserEntity(
    var email: String,
    var role: String,
    var password: String,
    var active: Boolean
) : EntityBase()
