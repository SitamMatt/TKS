//package test;
//
//import common.error.ErrorDto;
//import edu.p.lodz.pl.service.users.UserResource;
//import edu.p.lodz.pl.service.users.dto.UserDto;
//import org.junit.jupiter.api.Test;
//import org.microshed.testing.jaxrs.RESTClient;
//import org.microshed.testing.jupiter.MicroShedTest;
//import org.microshed.testing.testcontainers.ApplicationContainer;
//import org.testcontainers.junit.jupiter.Container;
//
//import javax.ws.rs.core.Response;
//import java.time.Duration;
//
//import static io.restassured.RestAssured.given;
//
//@MicroShedTest
//public class BasicJAXRSServiceTest {
//
//    // This will search for a Dockerfile in the repository and start up the application
//    // in a Docker container, and wait for it to be ready before starting the tests.
//    @Container
//    public static ApplicationContainer app = new ApplicationContainer()
//            .withAppContextRoot("/user-service/app/user")
//            .withReadinessPath("/user-service/app/user")
//            .withExposedPorts(4848, 8080, 9009)
//            .withHttpPort(8080)
//            .withEnv("PAYARA_ARGS", "--debug")
//            .withStartupTimeout(Duration.ofDays(1));
//
//    // This injects a REST _Client_ proxy of the PersonService shown above
//    // This allows us to easily invoke HTTP requests on the running application container
//    @RESTClient
//    public static UserResource personSvc;
//
//    @Test
//    public void testGetPerson() {
//        Response g = given()
//                .pathParam("id", "mszewc@edu.pl")
//                .when()
//                .get("/{id}");
//        UserDto b = g.body().as(UserDto.class);
//    }
//}
