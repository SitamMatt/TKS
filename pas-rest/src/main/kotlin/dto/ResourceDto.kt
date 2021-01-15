package dto

import java.util.*
import javax.validation.constraints.*

open class ResourceBaseDto {
    var type: ResourceType? = null
    @NotNull(message = "Title cannot be null. ")
    @NotEmpty(message = "Title cannot be empty. ")
    var title: String? = null
    @Positive(message = "Pages count must be positive value. ")
    var pagesCount = 0
    var publishingHouse: String? = null
    @NotNull(message = "Author cannot be null. ")
    @NotEmpty(message = "Author cannot be empty. ")
    var author: String? = null
    var issueDate: String? = null
}

class ResourceGetDto : ResourceBaseDto(){
    var guid: UUID? = null
}

enum class ResourceType{
    Magazine,
    Book
}

