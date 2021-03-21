package model

import java.util.*

class Event : Entity() {
    var rentDate: Date? = null
    var returnDate: Date? = null
    var userId: UUID? = null
    var resourceId: UUID? = null
}