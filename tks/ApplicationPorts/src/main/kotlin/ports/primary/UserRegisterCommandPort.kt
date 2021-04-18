package ports.primary

import domain.exceptions.DuplicatedEmailException
import domain.model.User

interface UserRegisterCommandPort {
    @Throws(DuplicatedEmailException::class)
    fun register(user: User)
}