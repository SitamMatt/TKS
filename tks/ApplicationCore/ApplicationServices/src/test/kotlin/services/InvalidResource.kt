package services

import model.Resource
import java.util.*

data class InvalidResource(
    override var id: UUID?,
    override var title: String
) : Resource()
