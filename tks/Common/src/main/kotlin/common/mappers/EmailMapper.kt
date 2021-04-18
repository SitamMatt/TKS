package common.mappers

import domain.model.values.Email
import org.mapstruct.Mapper
import org.mapstruct.Named

@Mapper
interface EmailMapper {

    @Named("toEmail")
    @JvmDefault
    fun toEmail(source: String?): Email? = if (source != null) Email(source) else null

    @Named("toEmailStr")
    @JvmDefault
    fun toString(source: Email?): String? = source?.value
}