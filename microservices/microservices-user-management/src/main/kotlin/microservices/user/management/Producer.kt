package microservices.user.management

import core.services.users.management.UsersManagementService
import ports.user.IUserRepositoryAdapter
import repositories.user.adapters.UserRepositoryAdapter
import repositories.user.repositories.UserRepository
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Produces

class Producer {

    @ApplicationScoped
    fun produceUserService(adapter: IUserRepositoryAdapter): UsersManagementService {
        return UsersManagementService(adapter, adapter)
    }

    @Produces
    fun produceUserRepositoryAdapter(repository: UserRepository): UserRepositoryAdapter {
        return UserRepositoryAdapter(repository)
    }
}