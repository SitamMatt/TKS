package fillers

import model.Event
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class NewEventsFiller {
    fun fill(): List<Event>? {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val result: MutableList<Event> = ArrayList()
        try {
            result.add(Event().apply {
                guid = UUID.fromString("fcb2c040-5b92-463d-bad8-818937d4f450")
                rentDate = sdf.parse("2012-01-01")
                returnDate = null
                userId = UUID.fromString("48bb061d-0a01-4f60-bdfc-f6bac839b107")
                resourceId = UUID.fromString("1514f5ae-f54d-4b4f-ac97-97f32fe18cb0")
            })
            result.add(Event().apply {
                guid = UUID.randomUUID()
                rentDate = sdf.parse("2013-03-01")
                returnDate = sdf.parse("2014-03-02")
                userId = UUID.fromString("48bb061d-0a01-4f60-bdfc-f6bac839b107")
                resourceId = UUID.fromString("c8168b00-b3e9-42da-afb9-1be9c44ceb44")
            });
        } catch (e: ParseException) {
            return null
        }
        return result
    }
}