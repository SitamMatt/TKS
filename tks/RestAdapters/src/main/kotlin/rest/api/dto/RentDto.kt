package rest.api.dto

import java.util.*

data class RentDto(
    var id: UUID? = null,
    var startDate: Date? = null,
    var endDate: Date? = null,
    var resourceAccessionNumber: String? = null,
    var userEmail: String? = null
)