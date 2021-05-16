package repository.mappers

import domain.model.context.rents.Client
import domain.model.values.Email
import repository.data.ClientEntity

class ClientMapper {

    fun mapEntityToDomainObject(src: ClientEntity?): Client? = if (src == null) null else Client(
        Email(src.email),
        src.active
    )
    companion object {
        val INSTANCE = ClientMapper()
    }

}