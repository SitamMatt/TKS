package model

import java.util.*

data class Rent(
    var id: UUID?,
    val startDate: Date,
    var endDate: Date?,
    val userEmail: String,
    val resourceId: UUID
)