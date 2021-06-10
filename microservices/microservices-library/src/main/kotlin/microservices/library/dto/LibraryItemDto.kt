package microservices.library.dto

data class LibraryResourceDto(
    var accessionNumber: String?,
    var locked: Boolean?,
    var title: String,
    var author: String?,
    var publisher: String?,
    var type: LibraryResourceType
)

data class LibraryResourceMessageDto(
    var accessionNumber: String?,
    var locked: Boolean?,
    var title: String?,
    var author: String?,
    var publisher: String?,
    var type: LibraryResourceType,
    var delted: Boolean = false
)



enum class LibraryResourceType{
    BOOK,
    MAGAZINE,
    NONE
}
