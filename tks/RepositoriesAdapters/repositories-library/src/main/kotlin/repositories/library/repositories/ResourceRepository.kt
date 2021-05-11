package repositories.library.repositories

import io.micronaut.context.annotation.Executable
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import repositories.library.entities.Book

@Repository
interface ResourceRepository : CrudRepository<Book, Long> {
    @Executable
    fun find(title: String): Book
}