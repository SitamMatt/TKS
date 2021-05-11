package microservices.library.controllers

import io.micronaut.data.repository.CrudRepository
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import repositories.library.entities.Book
import repositories.library.repositories.ResourceRepository
import javax.annotation.PostConstruct
import javax.inject.Inject
import javax.transaction.Transactional

@Controller("/library")
class LibraryController {

    @Inject
    private lateinit var resourceRepository: ResourceRepository

    @PostConstruct
    @Transactional
    fun init(){
        val book = Book(0,"Dune", 4)
        resourceRepository.save(book)
    }

    @Get("/")
    @Produces(MediaType.APPLICATION_JSON)
    fun get(): Book {
        return resourceRepository.find("Dune")
    }
}