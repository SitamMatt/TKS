package model

import model.values.AccessionNumber
import java.util.*

data class Magazine(
    override var id: AccessionNumber?,
    override var title: String,
    var publisher: String
) : Resource()
