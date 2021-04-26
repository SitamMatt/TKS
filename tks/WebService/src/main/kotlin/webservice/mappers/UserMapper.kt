package webservice.mappers

import domain.model.context.users.User
import webservice.dto.UserSoapDto

class UserMapper {

    fun toDto(src: User?): UserSoapDto? = if (src == null) null else UserSoapDto().apply {
        email = src.email.value
        active = src.active
        role = src.role.toString()
    }

    companion object {
        val INSTANCE: UserMapper = UserMapper()
    }
}