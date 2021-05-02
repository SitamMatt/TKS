package it.p.lodz.pl.service.rents.dto

data class LibraryItemDto(
    var accessionNumber: String? = null,
    var title: String? = null,
    var author: String? = null,
    var publisher: String? = null,
    var type: String? = null
)