package repository.repositories

import repository.data.EntityBase
import java.util.*

class RepositoryBase<T>(items: MutableList<T>) where T : EntityBase{
    private val store: MutableList<T> = items

    fun get(id: UUID): T? {
        return store.find { entity -> entity.guid == id }
    }

    fun add(entity: T){
        if(entity in store) throw Exception() // todo specify
        if(entity.guid != null) throw Exception() // todo specify
        entity.guid = UUID.randomUUID()
        store.add(entity)
    }

    fun update(entity: T){
        if(entity !in store) throw Exception() // todo specify
        store.remove(entity);
        store.add(entity);
    }

    fun remove(entity: T){
        if(entity !in store) throw Exception() // todo specify
        store.remove(entity)
    }

    fun find(predicate: (T) -> (Boolean)): T? {
        return store.find(predicate)
    }
}