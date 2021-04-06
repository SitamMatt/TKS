package domain.model.values;

import domain.exceptions.TypeValidationFailedException;
import lombok.Value;
import org.apache.commons.validator.routines.EmailValidator;

@Value
public class Email {
    String value;

    public Email(String value) throws TypeValidationFailedException {
        if(!EmailValidator.getInstance(false).isValid(value)) throw new TypeValidationFailedException();
        this.value = value;
    }
}
