package repository.data

abstract class AbstractResourceEntity : EntityBase() {
    abstract var accessionNumber: String?
    abstract var title: String
}

data class BookEntity(
    override var accessionNumber: String?,
    override var title: String,
    var author: String
) : AbstractResourceEntity()

data class MagazineEntity(
    override var accessionNumber: String?,
    override var title: String,
    var publisher: String
) : AbstractResourceEntity()