package core.domain.resource

import core.domain.common.valueobjects.AccessionNumber


interface Resource {
    var accessionNumber: core.domain.common.valueobjects.AccessionNumber?
    var title: String
    var locked: Boolean
}