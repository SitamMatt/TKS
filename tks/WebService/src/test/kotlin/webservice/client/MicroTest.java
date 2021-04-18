package webservice.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.microshed.testing.jupiter.MicroShedTest;
import org.microshed.testing.testcontainers.ApplicationContainer;
import org.testcontainers.junit.jupiter.Container;

@MicroShedTest
public class MicroTest {

    @Container
    public static ApplicationContainer application = new ApplicationContainer();

    @Test
    public void Test(){
        String a = "aa";
        Assertions.assertEquals(a, "aa");
    }
}
