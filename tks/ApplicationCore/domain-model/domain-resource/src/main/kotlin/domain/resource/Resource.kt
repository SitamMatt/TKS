package domain.resource

import domain.common.valueobjects.AccessionNumber


interface Resource {
    var accessionNumber: AccessionNumber?
    var title: String
    var locked: Boolean
}