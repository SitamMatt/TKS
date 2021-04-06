package services

import domain.model.Resource
import domain.model.values.AccessionNumber

data class InvalidResource(
    override var accessionNumber: AccessionNumber?,
    override var title: String
) : Resource()
