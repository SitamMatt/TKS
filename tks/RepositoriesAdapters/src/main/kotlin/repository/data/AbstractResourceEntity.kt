package repository.data

import java.util.*

abstract class AbstractResourceEntity : EntityBase() {

    abstract var accessionNumber: String?
    abstract var title: String
    abstract var isRent: Boolean
}

class BookEntity(
    override var guid: UUID?,
    override var accessionNumber: String?,
    override var title: String,
    override var isRent: Boolean,
    var author: String,
) : AbstractResourceEntity()

class MagazineEntity(
    override var guid: UUID?,
    override var accessionNumber: String?,
    override var title: String,
    override var isRent: Boolean,
    var publisher: String,
) : AbstractResourceEntity()