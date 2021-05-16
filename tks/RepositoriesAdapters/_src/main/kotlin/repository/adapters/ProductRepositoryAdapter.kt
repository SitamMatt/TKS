package repository.adapters

import domain.model.context.rents.Product
import domain.model.values.AccessionNumber
import ports.secondary.ProductSearchPort
import repository.data.ProductEntity
import repository.mappers.ProductMapper
import repository.repositories.IRepository

class ProductRepositoryAdapter(
    private val repository: IRepository<ProductEntity>,
    private val mapper: ProductMapper
) : ProductSearchPort {

    override fun findByAccessionNumber(accessionNumber: AccessionNumber): Product? {
        val entity = repository.find { x: ProductEntity -> x.accessionNumber == accessionNumber.value }
        return mapper.mapEntityToDomainObject(entity)
    }
}