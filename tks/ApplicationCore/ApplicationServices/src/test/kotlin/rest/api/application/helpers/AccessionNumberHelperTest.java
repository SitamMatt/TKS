package rest.api.application.helpers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccessionNumberHelperTest {

    @Test
    public void validityTest(){
        assertDoesNotThrow(AccessionNumberHelper::generate);
    }

}