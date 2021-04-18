package rest.api;

import org.junit.jupiter.api.Test;
import org.microshed.testing.jupiter.MicroShedTest;
import org.microshed.testing.testcontainers.ApplicationContainer;
import org.testcontainers.junit.jupiter.Container;
import rest.api.controllers.UserResource;

import javax.inject.Inject;

@MicroShedTest
public class MicroTest {

    @Container
    public static ApplicationContainer container = new ApplicationContainer();

    @Inject
    public UserResource resource;

    @Test
    public void test(){
        resource.get("mszewc@op.pl");
    }
}
