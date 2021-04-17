package webservice.mappers

import common.mappers.EmailMapper
import domain.model.User
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import webservice.dto.UserSoapDto

@Mapper(uses = [EmailMapper::class])
interface IUserMapper {

    fun toDto(source: User): UserSoapDto

    fun toDomainObject(source: UserSoapDto): User

    companion object{
        val INSTANCE: IUserMapper = Mappers.getMapper(IUserMapper::class.java)
    }
}