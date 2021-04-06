package mappers

import model.values.AccessionNumber
import kotlin.Throws
import exceptions.TypeValidationFailedException
import org.mapstruct.Mapper

@Mapper
interface AccessionNumberMapper {
    fun toValue(accessionNumber: AccessionNumber?): String? {
        return accessionNumber?.value
    }

    @Throws(TypeValidationFailedException::class)
    fun toAccessionNumber(value: String?): AccessionNumber? {
        return value?.let { AccessionNumber(it) }
    }
}