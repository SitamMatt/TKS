package repository.repositories

import repository.data.EntityBase
import java.util.*

interface IRepository<T> where T : EntityBase{
    fun get(id: UUID): T?
    fun add(entity: T)
    fun update(entity: T)
    fun remove(entity: T)
    fun find(predicate: (T) -> Boolean): T?
}