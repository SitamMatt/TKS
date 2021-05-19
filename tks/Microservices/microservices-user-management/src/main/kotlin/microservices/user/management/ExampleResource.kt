package microservices.user.management

import repositories.user.entities.UserEntity
import repositories.user.repositories.UserRepository
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/hello")
class ExampleResource(
    private val repository: UserRepository
) {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun hello(): MutableIterable<UserEntity> = repository.findAll()
}