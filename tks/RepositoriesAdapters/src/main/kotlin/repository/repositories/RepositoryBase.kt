package repository.repositories

import repository.data.EntityBase
import java.util.*

class RepositoryBase<T>(items: MutableList<T>) : IRepository<T> where T : EntityBase {
    private val store: MutableList<T> = items

    override fun get(id: UUID): T? {
        return store.find { entity -> entity.guid == id }
    }

    override fun add(entity: T) {
        if (entity in store) throw Exception() // todo specify
        if (entity.guid != null) throw Exception() // todo specify
        entity.guid = UUID.randomUUID()
        store.add(entity)
    }

    override fun update(entity: T) {
        if (entity !in store) {
            throw Exception() // todo specify
        }
        store.remove(entity);
        store.add(entity);
    }

    override fun remove(entity: T) {
        if (entity !in store) throw Exception() // todo specify
        store.remove(entity)
    }

    override fun find(predicate: (T) -> (Boolean)): T? {
        return store.find(predicate)
    }
}