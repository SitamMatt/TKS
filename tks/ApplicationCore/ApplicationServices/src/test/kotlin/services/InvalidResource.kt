package services

import model.Resource
import model.values.AccessionNumber
import java.util.*

data class InvalidResource(
    override var accessionNumber: AccessionNumber?,
    override var title: String
) : Resource()
