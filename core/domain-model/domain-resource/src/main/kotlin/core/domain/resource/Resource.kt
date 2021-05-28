package core.domain.resource

import core.domain.common.valueobjects.AccessionNumber


interface Resource {
    var accessionNumber: AccessionNumber?
    var title: String
    var locked: Boolean
}