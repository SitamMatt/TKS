package repository.mappers

import domain.model.context.rents.Product
import domain.model.values.AccessionNumber
import repository.data.ProductEntity

class ProductMapper {
    fun mapEntityToDomainObject(src: ProductEntity?): Product? = if (src == null) null else Product(
        AccessionNumber(src.accessionNumber!!),
    )

    companion object {
        val INSTANCE = ProductMapper()
    }
}