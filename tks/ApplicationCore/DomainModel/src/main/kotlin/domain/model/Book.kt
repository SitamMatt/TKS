package domain.model

import domain.model.traits.Resource
import domain.model.values.AccessionNumber

data class Book(
    override var accessionNumber: AccessionNumber?,
    override var title: String,
    var author: String
) : Resource
