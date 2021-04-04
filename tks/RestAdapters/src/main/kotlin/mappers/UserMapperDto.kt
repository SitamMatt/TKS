package mappers

import dto.UserDto
import model.User
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
interface UserMapperDto {
    fun toDomainObject(userDto: UserDto?): User?
    fun toDto(user: User): UserDto?

    companion object {
        val INSTANCE:UserMapperDto = Mappers.getMapper(UserMapperDto::class.java)
    }
}