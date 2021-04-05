package model

import model.values.AccessionNumber

abstract class Resource {
    abstract var accessionNumber: AccessionNumber?
    abstract var title: String
}