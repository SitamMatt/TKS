package model.values;

import exceptions.TypeValidationFailedException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccessionNumberTest {

    @Test
    public void validationTest(){
        assertThrows(TypeValidationFailedException.class, () -> new AccessionNumber("xxx"));
        assertThrows(TypeValidationFailedException.class, () -> new AccessionNumber("AAAA-5555"));
        assertThrows(TypeValidationFailedException.class, () -> new AccessionNumber("AAAA-55"));
        assertDoesNotThrow(() -> new AccessionNumber("SCXI-591"));
    }

    @SneakyThrows
    @Test
    public void equalityTest(){
        var accessionNumber1 = new AccessionNumber("SCXI-591");
        var accessionNumber2 = new AccessionNumber("SCXI-591");
        var accessionNumber3 = new AccessionNumber("SCXI-592");

        assertNotSame(accessionNumber1, accessionNumber2);
        assertEquals(accessionNumber1, accessionNumber2);
        assertNotEquals(accessionNumber1, accessionNumber3);
    }

}