package domain.model.context.library

import domain.model.values.AccessionNumber

data class Book(
    override var accessionNumber: AccessionNumber?,
    override var title: String,
    override var locked: Boolean,
    var author: String,
) : Resource
