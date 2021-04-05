package model.values;

import exceptions.TypeValidationFailedException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @SneakyThrows
    @Test
    public void equalityTest(){
        var email1 = new Email("mszewc@edu.pl");
        var email2 = new Email("mszewc@edu.pl");
        var email3 = new Email("mzab@edu.pl");

        assertNotSame(email1, email2);
        assertEquals(email1, email2);
        assertNotEquals(email1, email3);
    }

    @Test
    public void constructorValidationTest(){
        assertDoesNotThrow(() -> new Email("mszewc@edu.pl"));

        assertThrows(TypeValidationFailedException.class, () -> new Email("mszewc"));

        assertThrows(TypeValidationFailedException.class, () -> new Email("mszewc@edu.pl@hehe"));

        assertThrows(TypeValidationFailedException.class, () -> new Email(null));
        assertThrows(TypeValidationFailedException.class, () -> new Email(""));
    }

}