package domain.model.context.library

import domain.model.values.AccessionNumber

data class Magazine(
    override var accessionNumber: AccessionNumber?,
    override var title: String,
    override var locked: Boolean,
    var publisher: String,
) : Resource
