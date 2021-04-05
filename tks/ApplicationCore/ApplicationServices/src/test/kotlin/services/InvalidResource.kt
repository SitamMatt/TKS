package services

import model.Resource
import model.values.AccessionNumber
import java.util.*

data class InvalidResource(
    override var id: AccessionNumber?,
    override var title: String
) : Resource()
