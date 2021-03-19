package data

import java.util.*

abstract class AbstractResourceEntity : EntityBase() {
    abstract var id: UUID?
    abstract var title: String
}

data class BookEntity(
    override var id: UUID?,
    override var title: String,
    var author: String
) : AbstractResourceEntity()

data class MagazineEntity(
    override var id: UUID?,
    override var title: String,
    var publisher: String
) : AbstractResourceEntity()