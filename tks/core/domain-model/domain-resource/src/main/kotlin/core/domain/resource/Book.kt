package core.domain.resource

import core.domain.common.valueobjects.AccessionNumber

data class Book(
    override var accessionNumber: AccessionNumber?,
    override var title: String,
    override var locked: Boolean,
    var author: String,
) : Resource
