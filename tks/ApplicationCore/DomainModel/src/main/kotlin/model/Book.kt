package model

import model.values.AccessionNumber
import java.util.*

data class Book(
    override var id: AccessionNumber?,
    override var title: String,
    var author: String
) : Resource()
