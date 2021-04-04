package model.values;

import exceptions.TypeValidationFailedException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @SneakyThrows
    @Test
    public void equalityTest(){
        var email1 = new Email("mszewc@edu.pl");
        var email2 = new Email("mszewc@edu.pl");
        var email3 = new Email("mzab@edu.pl");

        Assertions.assertNotSame(email1, email2);
        Assertions.assertEquals(email1, email2);
        Assertions.assertNotEquals(email1, email3);
    }

    @Test
    public void constructorValidationTest(){
        Assertions.assertDoesNotThrow(() -> new Email("mszewc@edu.pl"));

        Assertions.assertThrows(TypeValidationFailedException.class, () -> new Email("mszewc"));

        Assertions.assertThrows(TypeValidationFailedException.class, () -> new Email("mszewc@edu.pl@hehe"));
    }

}