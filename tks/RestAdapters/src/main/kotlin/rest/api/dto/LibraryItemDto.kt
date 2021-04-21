package rest.api.dto

class LibraryItemDto() {
    var accessionNumber: String? = null
    var title: String? = null
    var author: String? = null
    var publisher: String? = null
    var type: String? = null

    constructor(accessionNumber: String?, title: String?, author: String?, publisher: String?, type: String?) : this() {
        this.accessionNumber = accessionNumber
        this.title = title
        this.author = author
        this.publisher = publisher
        this.type = type
    }
}