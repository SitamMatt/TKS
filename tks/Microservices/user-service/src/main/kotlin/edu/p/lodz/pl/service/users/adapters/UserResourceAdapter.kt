package edu.p.lodz.pl.service.users.adapters

import domain.exceptions.DuplicatedEmailException
import domain.exceptions.TypeValidationFailedException
import domain.exceptions.UserNotFoundException
import domain.model.values.Email
import edu.p.lodz.pl.service.users.dto.UserDto
import edu.p.lodz.pl.service.users.mappers.UserMapperDto
import ports.primary.combined.IUserService
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
    fun queryUser(email: String?): UserDto {
        val emailObject = Email(email!!)
        val user = userService.getDetails(emailObject)
        return mapper.toDto(user!!)
    }
}