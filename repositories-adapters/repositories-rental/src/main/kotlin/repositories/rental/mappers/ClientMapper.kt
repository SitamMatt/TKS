package repositories.rental.mappers

import core.domain.common.valueobjects.Email
import core.domain.rent.Client
import repositories.rental.entities.ClientEntity

fun ClientEntity.toDomain() = Client(
    Email(email),
    active
)

fun Client.toEntity(): ClientEntity {
    val entity = ClientEntity(
        email.value,
        active
    )
    return entity
}

fun Client.toEntity(clientEntity: ClientEntity){
    clientEntity.active = active
}

