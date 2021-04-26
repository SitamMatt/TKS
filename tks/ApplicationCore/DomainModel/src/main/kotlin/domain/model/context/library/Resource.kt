package domain.model.context.library

import domain.model.values.AccessionNumber

interface Resource {
    var accessionNumber: AccessionNumber?
    var title: String
    var isRent: Boolean
}