package ports.secondary

import domain.model.User
import domain.model.values.Email

interface UserSearchPort {
    fun findByEmail(email: Email): User?
}