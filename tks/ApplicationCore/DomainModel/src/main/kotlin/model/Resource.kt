package model

import model.values.AccessionNumber

abstract class Resource {
    abstract var id: AccessionNumber?
    abstract var title: String
}