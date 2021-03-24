package model

import java.util.*

data class Book(
    override var id: UUID?,
    override var title: String,
    var author: String
) : Resource()
