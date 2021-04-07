package domain.model

import domain.model.values.AccessionNumber

abstract class Resource {
    abstract var accessionNumber: AccessionNumber?
    abstract var title: String
}