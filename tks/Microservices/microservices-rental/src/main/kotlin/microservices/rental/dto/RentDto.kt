package microservices.rental.dto

import microservices.rental.controllers.RentResource
import microservices.rental.links.LinkListAdapter
import microservices.rental.links.LinkProperty
import microservices.rental.links.RemoteLinkProperty
import java.util.*
import javax.json.bind.annotation.JsonbTypeAdapter
import javax.ws.rs.core.Link

class RentDto {
    @LinkProperty(
        rel = "self",
        method = "get",
        resource = RentResource::class
    )
    var id: UUID

    var startDate: Date
    var endDate: Date? = null

    @RemoteLinkProperty(
        uriPath = "apis.client.uri",
        rel = "product"
    )
    var productId: String

    @RemoteLinkProperty(
        uriPath = "apis.product.uri",
        rel = "client"
    )
    var clientId: String

    @JsonbTypeAdapter(LinkListAdapter::class)
    var links = ArrayList<Link>()

    constructor(id: UUID, startDate: Date, endDate: Date?, productId: String, clientId: String) {
        this.id = id
        this.startDate = startDate
        this.endDate = endDate
        this.productId = productId
        this.clientId = clientId
    }
}