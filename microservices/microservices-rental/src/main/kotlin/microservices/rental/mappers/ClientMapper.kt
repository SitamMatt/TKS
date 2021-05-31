package microservices.rental.mappers

import core.domain.common.valueobjects.Email
import core.domain.rent.Client
import microservices.rental.dto.ClientDto

fun ClientDto.toDomain() = Client(Email(email), active)