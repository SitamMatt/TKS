package domain.model

import domain.model.traits.Resource
import domain.model.values.AccessionNumber

data class Magazine(
    override var accessionNumber: AccessionNumber?,
    override var title: String,
    var publisher: String
) : Resource
