package model

import model.values.AccessionNumber
import java.util.*

data class Book(
    override var accessionNumber: AccessionNumber?,
    override var title: String,
    var author: String
) : Resource()
