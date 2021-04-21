package rest.api.adapters

import domain.exceptions.DuplicatedEmailException
import domain.exceptions.TypeValidationFailedException
import domain.exceptions.UserNotFoundException
import domain.model.values.Email
import ports.primary.combined.IUserService
import rest.api.dto.UserDto
import rest.api.mappers.UserMapperDto
import javax.inject.Inject

class UserResourceAdapter @Inject constructor(
    private val userService: IUserService,
    private val mapper: UserMapperDto
) {

    @Throws(DuplicatedEmailException::class)
    fun registerCommand(dto: UserDto?) {
        val user = mapper.toDomainObject(dto) ?: throw Exception()
        userService.register(user)
    }

    @Throws(TypeValidationFailedException::class, UserNotFoundException::class)
    fun queryUser(email: String?): UserDto? {
        val emailObject = Email(email!!)
        val user = userService.getDetails(emailObject)
        return mapper.toDto(user!!)
    }
}