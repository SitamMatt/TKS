package repositories.user.repositories

import org.springframework.data.repository.CrudRepository
import repositories.user.entities.UserEntity

interface UserRepository : CrudRepository<UserEntity, Long>{

    fun findByEmail(email: String): UserEntity?
}