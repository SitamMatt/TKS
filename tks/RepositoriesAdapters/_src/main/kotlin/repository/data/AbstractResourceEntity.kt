package repository.data

import java.util.*

abstract class AbstractResourceEntity : EntityBase() {
    abstract var accessionNumber: String?
    abstract var title: String
    abstract var locked: Boolean
}

class BookEntity(
    override var guid: UUID?,
    override var accessionNumber: String?,
    override var title: String,
    override var locked: Boolean,
    var author: String,
) : AbstractResourceEntity()

class MagazineEntity(
    override var guid: UUID?,
    override var accessionNumber: String?,
    override var title: String,
    override var locked: Boolean,
    var publisher: String,
) : AbstractResourceEntity()