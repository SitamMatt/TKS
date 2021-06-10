package repositories.rental.adapters

import core.domain.common.valueobjects.AccessionNumber
import core.domain.rent.Product
import ports.rent.IProductRepositoryAdapter
import repositories.rental.mappers.toDomain
import repositories.rental.mappers.toEntity
import repositories.rental.repositories.ProductRepository

class ProductRepositoryAdapter(
    private val productRepository: ProductRepository
) : IProductRepositoryAdapter {

    override fun findByAccessionNumber(accessionNumber: AccessionNumber): Product? {
        return try {
            val result = productRepository.findByAccessionNumber(accessionNumber.value)
            if (!result.isPresent) return null
            return result.get().toDomain()
        } catch (ex: Exception) {
            null
        }
    }

    fun save(product: Product) {
        try {
            val result = productRepository.findByAccessionNumber(product.accessionNumber!!.value)
            if (!result.isPresent) {
                val entity = product.toEntity()
                productRepository.save(entity)
            } else {
                val entity = result.get()
                product.toEntity(entity)
                productRepository.save(entity)
            }
        } catch (ex: Exception) {
            throw ex
        }
    }

    fun delete(accessionNumber: AccessionNumber){
        try{
            val result = productRepository.findByAccessionNumber(accessionNumber.value)
            if(result.isPresent){
                productRepository.remove(result.get())
            }
        }catch (ex: Exception){
            throw ex
        }
    }
}