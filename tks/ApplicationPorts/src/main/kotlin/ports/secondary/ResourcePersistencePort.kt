package ports.secondary

import domain.model.traits.Resource

interface ResourcePersistencePort {
    fun add(resource: Resource)
    fun save(resource: Resource)
    fun remove(resource: Resource)
}