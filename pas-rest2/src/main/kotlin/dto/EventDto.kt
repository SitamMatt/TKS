package dto

import java.util.*
import javax.json.bind.annotation.JsonbDateFormat

class EventDto{
    var guid: UUID? = null
    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ssXXX")
    var rentDate: Date? = null
    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ssXXX")
    var returnDate: Date? = null
    var userId: UUID? = null
    var resourceId: UUID? = null
}
