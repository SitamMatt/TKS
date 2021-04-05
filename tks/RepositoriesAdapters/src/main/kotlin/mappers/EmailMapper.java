package mappers;

import exceptions.TypeValidationFailedException;
import model.values.Email;
import org.mapstruct.Mapper;

@Mapper
public interface EmailMapper {

    default String toValue(Email email){
        return email.getValue();
    }

    default Email toEmail(String value) throws TypeValidationFailedException {
        return new Email(value);
    }
}
