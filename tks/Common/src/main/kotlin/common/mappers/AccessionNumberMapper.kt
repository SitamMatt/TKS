package common.mappers

import domain.model.values.AccessionNumber
import org.mapstruct.Mapper

@Mapper
interface AccessionNumberMapper {

    @JvmDefault
    fun toAccessionNumber(source: String?): AccessionNumber? = if (source != null) AccessionNumber(source) else null

    @JvmDefault
    fun toString(source: AccessionNumber?): String? = source?.value
}