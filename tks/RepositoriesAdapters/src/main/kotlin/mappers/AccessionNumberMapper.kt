package mappers

import model.values.AccessionNumber
import kotlin.Throws
import exceptions.TypeValidationFailedException
import org.mapstruct.Mapper

@Mapper
interface AccessionNumberMapper {
    @JvmDefault
    fun toValue(accessionNumber: AccessionNumber): String? {
        return accessionNumber.value
    }

    @JvmDefault
    @Throws(TypeValidationFailedException::class)
    fun toAccessionNumber(value: String?): AccessionNumber? {
        return AccessionNumber(value)
    }
}