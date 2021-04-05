package data

import java.util.*

abstract class AbstractResourceEntity : EntityBase() {
    abstract var id: String?
    abstract var title: String
}

data class BookEntity(
    override var id: String?,
    override var title: String,
    var author: String
) : AbstractResourceEntity()

data class MagazineEntity(
    override var id: String?,
    override var title: String,
    var publisher: String
) : AbstractResourceEntity()