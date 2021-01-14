package dto

import java.util.*

open class ResourceBaseDto {
//    var type: ResourceType? = null
    var title: String? = null
    var pagesCount = 0
    var publishingHouse: String? = null
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

