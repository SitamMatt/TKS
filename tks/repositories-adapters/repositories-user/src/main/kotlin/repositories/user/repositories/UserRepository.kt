package repositories.user.repositories

import org.springframework.data.repository.CrudRepository
import repositories.user.entities.UserEntity
import java.util.*

interface UserRepository : CrudRepository<UserEntity, Long>{

    fun findByEmail(email: String): UserEntity?
}