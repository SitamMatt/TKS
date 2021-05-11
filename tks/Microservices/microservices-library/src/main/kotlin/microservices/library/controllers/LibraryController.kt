package microservices.library.controllers

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces

@Controller("/library")
class LibraryController {

    @Get("/")
    @Produces(MediaType.TEXT_PLAIN)
    fun get(): String {
        return "Hello"
    }
}