package model

import java.util.*

data class Rent(
    val startDate: Date,
    val endDate: Date?,
    val userEmail: String,
    val reosurceId: UUID
)