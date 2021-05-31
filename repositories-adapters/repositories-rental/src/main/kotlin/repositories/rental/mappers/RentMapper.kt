package repositories.rental.mappers

import core.domain.common.valueobjects.AccessionNumber
import core.domain.common.valueobjects.Email
import core.domain.rent.Rent
import repositories.rental.entities.ClientEntity
import repositories.rental.entities.ProductEntity
import repositories.rental.entities.RentEntity

fun RentEntity.toDomain(): Rent = Rent(
    identifier,
    startDate,
    endDate,
    Email(client.email),
    AccessionNumber(product.accessionNumber)
)

fun Rent.toEntity(clientEntity: ClientEntity, productEntity: ProductEntity): RentEntity = RentEntity(
    0,
    id!!,
    startDate,
    endDate,
    clientEntity,
    productEntity
)

fun Rent.toEntity(rentEntity: RentEntity, clientEntity: ClientEntity, productEntity: ProductEntity){
    rentEntity.let {
        it.identifier = this.id!!
        it.startDate = this.startDate
        it.endDate = this.endDate
        it.client = clientEntity
        it.product = productEntity
    }
}