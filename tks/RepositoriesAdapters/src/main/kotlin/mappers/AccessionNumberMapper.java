package mappers;

import exceptions.TypeValidationFailedException;
import model.values.AccessionNumber;
import org.mapstruct.Mapper;

@Mapper
public interface AccessionNumberMapper {

    default String toValue(AccessionNumber accessionNumber){
        return accessionNumber.getValue();
    }

    default AccessionNumber toAccessionNumber(String value) throws TypeValidationFailedException {
        return new AccessionNumber(value);
    }
}
