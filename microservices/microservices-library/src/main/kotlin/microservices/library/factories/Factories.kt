package microservices.library.factories

import core.services.resources.management.ResourcesManagementService
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import repositories.library.adapters.ResourceRepositoryAdapter
import javax.inject.Singleton

@Factory
class Factories {

    @Bean
    @Singleton
    fun resService(adapter: ResourceRepositoryAdapter): ResourcesManagementService {
        return ResourcesManagementService(adapter, adapter)
    }
}