package domain.resource

import domain.common.valueobjects.AccessionNumber

data class Magazine(
    override var accessionNumber: AccessionNumber?,
    override var title: String,
    override var locked: Boolean,
    var publisher: String,
) : Resource
