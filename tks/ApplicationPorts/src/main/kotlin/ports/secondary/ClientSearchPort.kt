package ports.secondary

import domain.model.User
import domain.model.context.rents.Client
import domain.model.values.Email

interface ClientSearchPort {

    fun findByEmail(email: Email): Client?
}