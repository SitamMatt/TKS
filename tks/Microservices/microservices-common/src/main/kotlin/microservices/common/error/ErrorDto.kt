package microservices.common.error

data class ErrorDto (
    var message: String? = null,
    var code: Int? = null


){
    constructor(status: Status) : this(status.message, status.code)
}