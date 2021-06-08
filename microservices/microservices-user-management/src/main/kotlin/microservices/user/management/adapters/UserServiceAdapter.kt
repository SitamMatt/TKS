package microservices.user.management.adapters

import core.domain.common.UserRole
import core.domain.common.exceptions.UserNotFoundException
import core.domain.common.valueobjects.Email
import core.domain.user.User
import microservices.user.management.dto.UserDto
import microservices.user.management.mappers.toDomain
import microservices.user.management.mappers.toDto
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter
import ports.user.IUserService
import javax.enterprise.context.RequestScoped
import javax.inject.Inject

@RequestScoped
open class UserServiceAdapter @Inject constructor(
    private val userService: IUserService,
    @Channel("clients-out")
    private val userEmitter: Emitter<User>
) {

    open fun registerUser(user: UserDto): UserDto {
        try {
            val domain = user.toDomain()
            val result = userService.register(domain)
            userEmitter.send(result)
            return result.toDto()
        } catch (ex: Exception) {
            println(ex)
            throw ex
        }
    }

    open fun queryUser(email: String): UserDto {
        val user = userService.getDetails(Email(email)) ?: throw Exception()
        return user.toDto()
    }

    @Throws(UserNotFoundException::class)
    fun changeState(email: String, state: Boolean) {
        try {
            userService.changeState(Email(email), state)
        }catch (ex: Exception){
            throw ex
        }
    }

    @Throws(UserNotFoundException::class)
    fun changeRole(email: String, role: String) {
        try {
            userService.changeRole(Email(email), UserRole.valueOf(role))
        }catch (ex: Exception){
            throw ex
        }
    }
}