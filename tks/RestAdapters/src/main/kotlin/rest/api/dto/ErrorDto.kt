package rest.api.dto

data class ErrorDto (
    var message: String? = null,
    var code: Int? = null
)