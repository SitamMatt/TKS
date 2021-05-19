package microservices.user.management

import io.quarkus.runtime.Startup
import repositories.user.entities.UserEntity
import repositories.user.repositories.UserRepository
import javax.enterprise.context.ApplicationScoped

@Startup
@ApplicationScoped
open class StartupBean(
    private val repo: UserRepository
) {

    init{
        repo.save(UserEntity("mszewc@edu.pl", "ADMIN", "####", true))
    }

}