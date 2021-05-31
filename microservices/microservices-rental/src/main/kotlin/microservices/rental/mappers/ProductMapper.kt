package microservices.rental.mappers

import core.domain.common.valueobjects.AccessionNumber
import core.domain.rent.Product
import microservices.rental.dto.ProductDto

fun ProductDto.toDomain() = Product(AccessionNumber(accessionNumber))