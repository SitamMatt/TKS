package webservice.adapters

import application.services.UserService
import domain.exceptions.TypeValidationFailedException
import domain.exceptions.UserNotFoundException
import domain.model.values.Email
import ports.primary.combined.IUserService
import webservice.dto.UserSoapDto
import webservice.mappers.UserMapper
import javax.inject.Inject

class UserWebServiceAdapter(
    @Inject private var userService: IUserService? = null,
    @Inject private var mapper: UserMapper? = null
) {


    @Throws(TypeValidationFailedException::class, UserNotFoundException::class)
    fun getUser(email: String): UserSoapDto {
        val key = Email(email)
        val user = userService!!.getDetails(key)
        return mapper!!.toDto(user)!!
    }
}