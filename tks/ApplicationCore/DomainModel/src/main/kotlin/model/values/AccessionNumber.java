package model.values;

import exceptions.TypeValidationFailedException;
import lombok.Value;

@Value
public class AccessionNumber {
    String value;

    public AccessionNumber(String value) throws TypeValidationFailedException {
        if(!value.matches(format)) throw new TypeValidationFailedException();
        this.value = value;
    }

    static final String format = "\\b[A-Z]{4}-[0-9]{3}\\b";
}
