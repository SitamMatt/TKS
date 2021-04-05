package model

import model.values.AccessionNumber
import model.values.Email
import java.util.*

data class Rent(
    var id: UUID?,
    val startDate: Date,
    var endDate: Date?,
    val userEmail: Email,
    val resourceId: AccessionNumber
)