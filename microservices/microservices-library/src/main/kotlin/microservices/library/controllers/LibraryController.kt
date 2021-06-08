package microservices.library.controllers

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import microservices.common.error.ErrorDto
import microservices.common.error.ResourceNotFoundException
import microservices.library.adapters.LibraryControllerAdapter
import microservices.library.dto.LibraryResourceDto
import javax.inject.Inject

@Controller("/library")
class LibraryController {

    @Inject
    lateinit var adapter: LibraryControllerAdapter

    @Get("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun get(@PathVariable id: String): HttpResponse<Any> = adapter.query(id)
        .map {
            return HttpResponse.ok(it)
        }.getOrElse {
            return when (it) {
                is ResourceNotFoundException -> HttpResponse.notFound<ErrorDto>().body(ErrorDto(it.status))
                else -> HttpResponse.serverError<ErrorDto>().body(ErrorDto())
            }
        }

    @Post
    @Produces(MediaType.APPLICATION_JSON)
    fun post(@Body model: LibraryResourceDto): HttpResponse<Any> = adapter.add(model)
        .map {
            return HttpResponse.created(it)
        }.getOrElse {
            return when (it) {
                is ResourceNotFoundException -> HttpResponse.notFound<ErrorDto>().body(ErrorDto(it.status))
                else -> HttpResponse.serverError<ErrorDto>().body(ErrorDto())
            }
        }

    @Put("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun put(@PathVariable id: String, @Body model: LibraryResourceDto): HttpResponse<Any> = adapter.update(id, model)
        .map {
            return HttpResponse.ok(it)
        }.getOrElse {
            return when (it) {
                is ResourceNotFoundException -> HttpResponse.notFound<ErrorDto>().body(ErrorDto(it.status))
                else -> HttpResponse.serverError<ErrorDto>().body(ErrorDto())
            }
        }

    @Delete("/{id}")
    fun delete(@PathVariable id: String): HttpResponse<Any> = adapter.remove(id)
        .map {
            return HttpResponse.ok()
        }.getOrElse {
            return when (it) {
                is ResourceNotFoundException -> HttpResponse.notFound<ErrorDto>().body(ErrorDto(it.status))
                else -> HttpResponse.serverError<ErrorDto>().body(ErrorDto())
            }
        }
}