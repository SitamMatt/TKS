package repository.data

import java.util.*

abstract class AbstractResourceEntity : EntityBase() {

    abstract var accessionNumber: String?
    abstract var title: String
}

class BookEntity(
    override var guid: UUID?,
    override var accessionNumber: String?,
    override var title: String,
    var author: String
) : AbstractResourceEntity()

class MagazineEntity(
    override var guid: UUID?,
    override var accessionNumber: String?,
    override var title: String,
    var publisher: String,
) : AbstractResourceEntity()