package repositories.rental.mappers

import core.domain.common.valueobjects.AccessionNumber
import core.domain.rent.Product
import repositories.rental.entities.ProductEntity


fun ProductEntity.toDomain() = Product(
    AccessionNumber(accessionNumber)
)

fun Product.toEntity(): ProductEntity = ProductEntity(0, accessionNumber!!.value)

fun Product.toEntity(dest: ProductEntity) {
    dest.accessionNumber = accessionNumber!!.value
}