package webservices

import jakarta.jws.WebService

@WebService(endpointInterface = "webservices.UserWebservice")
open class UserWebserviceImpl : UserWebservice {
    override fun find(id: String): UserDto {
        val dto = UserDto()
        dto.email = "mszewc@edu.pl"
        return dto
    }
}