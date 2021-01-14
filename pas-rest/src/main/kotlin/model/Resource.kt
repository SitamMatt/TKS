package model

open class Resource constructor() : Entity() {
    var title: String? = null
    var pagesCount = 0
    var publishingHouse: String? = null
}

class Book constructor() : Resource(){
    var author: String? = null
}

class Magazine constructor() : Resource(){
    var issueDate: String? = null
}