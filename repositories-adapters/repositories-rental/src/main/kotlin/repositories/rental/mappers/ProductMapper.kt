package repositories.rental.mappers

import core.domain.common.valueobjects.AccessionNumber
import core.domain.rent.Product
import repositories.rental.entities.ProductEntity


fun ProductEntity.toDomain() = Product(
    AccessionNumber(accessionNumber)
)

fun Product.toEntity(): ProductEntity {
    val res = ProductEntity(accessionNumber!!.value)
    return res
}

fun Product.toEntity(dest: ProductEntity){
    dest.accessionNumber = accessionNumber!!.value
}