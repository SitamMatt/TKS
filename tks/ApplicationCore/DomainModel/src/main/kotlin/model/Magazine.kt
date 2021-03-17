package model

import java.util.*

data class Magazine(
    override var id: UUID,
    override var title: String,
    override var type: ResourceType,
    var publisher: String
) : Resource()
