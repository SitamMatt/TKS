package model

import java.util.*

abstract class Resource {
    abstract var id: UUID
    abstract var type: ResourceType
    abstract var title: String
}