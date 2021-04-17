package repository.data

import java.util.*

abstract class EntityBase(
    var guid: UUID? = null
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EntityBase

        if (guid != other.guid) return false

        return true
    }

    override fun hashCode(): Int {
        return guid?.hashCode() ?: 0
    }
}