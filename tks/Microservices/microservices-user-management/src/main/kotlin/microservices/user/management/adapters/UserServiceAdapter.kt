package microservices.user.management.adapters

//import ports.user.IUserService
import core.domain.common.valueobjects.Email
import microservices.user.management.dto.UserDto
import microservices.user.management.mappers.toDto
import ports.user.IUserService
import java.lang.Exception
import javax.enterprise.context.RequestScoped
import javax.inject.Inject
@RequestScoped
open class UserServiceAdapter @Inject constructor(
    private val userService: IUserService
) {

    open fun registerUser(){
        try{

        }catch(ex: Exception){

        }
    }

    open fun queryUser(email: String): UserDto {
        val user = userService.getDetails(Email(email)) ?: throw Exception()
        return user.toDto()
    }
}