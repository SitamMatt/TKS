package ports.primary

import domain.exceptions.UserNotFoundException
import domain.model.context.users.User
import domain.model.values.Email

interface UserQueryPort {
    @Throws(UserNotFoundException::class)
    fun getDetails(email: Email): User?
}