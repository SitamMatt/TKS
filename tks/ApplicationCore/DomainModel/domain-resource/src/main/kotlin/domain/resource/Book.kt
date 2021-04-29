package domain.resource

import domain.common.valueobjects.AccessionNumber

data class Book(
    override var accessionNumber: AccessionNumber?,
    override var title: String,
    override var locked: Boolean,
    var author: String,
) : Resource
