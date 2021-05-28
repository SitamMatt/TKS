package repositories.library

import io.micronaut.discovery.event.ServiceReadyEvent
import io.micronaut.runtime.event.annotation.EventListener
import io.micronaut.scheduling.annotation.Async
import repositories.library.entities.BookEntity
import repositories.library.repositories.ResourceRepository
import javax.inject.Singleton
import javax.transaction.Transactional


@Singleton
open class DataLoader(
    private val repository: ResourceRepository
) {

    @EventListener
    @Async
    @Transactional
    open fun loadData(event: ServiceReadyEvent) {
        val bookEntity = BookEntity()
        bookEntity.accessionNumber = "EEEE-254"
        bookEntity.author = "Frank Herbert"
        bookEntity.locked = false
        bookEntity.title = "Dune"
        repository.save(bookEntity)
    }
}