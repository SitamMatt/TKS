package common.mappers

import domain.model.values.Email
import org.mapstruct.Mapper

@Mapper
interface EmailMapper {

    @JvmDefault
    fun toEmail(source: String?): Email? = if(source != null) Email(source) else null

    @JvmDefault
    fun toString(source: Email?): String? = source?.value
}