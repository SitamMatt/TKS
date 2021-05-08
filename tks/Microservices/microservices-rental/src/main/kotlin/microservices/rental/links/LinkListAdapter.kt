package microservices.rental.links

import javax.json.Json
import javax.json.JsonArray
import javax.json.bind.adapter.JsonbAdapter
import javax.ws.rs.core.Link

class LinkListAdapter : JsonbAdapter<List<Link>, JsonArray> {
    override fun adaptToJson(p0: List<Link>?): JsonArray? {
        val arr = Json.createArrayBuilder()
        for (link in p0!!) {
            arr.add(Json.createObjectBuilder()
                .add("rel", link.rel)
                .add("href", link.uri.toString())
                .build())
        }
        return arr.build()
    }

    override fun adaptFromJson(p0: JsonArray?): List<Link>? {
        return null
    }
}