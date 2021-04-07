package rest.api.dto

data class TokenDto(
    val token: String,
    val expires: String
)