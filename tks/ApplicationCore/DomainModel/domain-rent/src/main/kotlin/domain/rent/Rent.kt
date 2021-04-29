package domain.rent

import domain.common.valueobjects.AccessionNumber
import domain.common.valueobjects.Email
import java.util.*

data class Rent(
    var id: UUID?,
    val startDate: Date,
    var endDate: Date?,
    val userEmail: Email,
    val resourceId: AccessionNumber
)