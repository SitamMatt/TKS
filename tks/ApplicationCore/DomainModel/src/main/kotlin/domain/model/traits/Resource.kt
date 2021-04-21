package domain.model.traits

import domain.model.values.AccessionNumber

interface Resource {
    var accessionNumber: AccessionNumber?
    var title: String
}