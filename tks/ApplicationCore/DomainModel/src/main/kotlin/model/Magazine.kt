package model

import java.util.*

data class Magazine(
    override var id: UUID?,
    override var title: String,
    var publisher: String
) : Resource()
