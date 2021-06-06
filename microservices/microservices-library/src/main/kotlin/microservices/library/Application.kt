package microservices.library

import io.micronaut.runtime.Micronaut.*
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info

@OpenAPIDefinition(
    info = Info(
        title = "Library microservice",
        version = "0.1"
    )
)
object Application

fun main(args: Array<String>) {
    build()
        .args(*args)
        .packages("microservices.library")
        .start()
}


