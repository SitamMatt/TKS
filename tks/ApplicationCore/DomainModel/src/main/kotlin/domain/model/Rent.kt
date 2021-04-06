package domain.model

import domain.model.values.AccessionNumber
import domain.model.values.Email
import java.util.*

data class Rent(
    var id: UUID?,
    val startDate: Date,
    var endDate: Date?,
    val userEmail: Email,
    val resourceId: AccessionNumber
)