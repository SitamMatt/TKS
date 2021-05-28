package core.domain.resource

import core.domain.common.valueobjects.AccessionNumber

data class Magazine(
    override var accessionNumber: core.domain.common.valueobjects.AccessionNumber?,
    override var title: String,
    override var locked: Boolean,
    var publisher: String,
) : Resource
