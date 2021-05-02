package microservices.common.error

data class ErrorDto (
    var message: String? = null,
    var code: Int? = null
)