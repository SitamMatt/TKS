package model

import java.util.*

data class Book(
    override var id: UUID,
    override var title: String,
    override var type: ResourceType,
    var author: String
) : Resource()
