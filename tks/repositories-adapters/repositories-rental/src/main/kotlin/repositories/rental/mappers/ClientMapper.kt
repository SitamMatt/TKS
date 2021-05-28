package repositories.rental.mappers

import core.domain.common.valueobjects.Email
import core.domain.rent.Client
import repositories.rental.entities.ClientEntity

fun ClientEntity.toDomain() = Client(
    Email(email),
    active
)

