package webservice.client;

import client.TypeValidationFailedException_Exception;
import client.UserNotFoundException_Exception;
import client.UserService;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class HelloTest {

    @Test
    public void test() throws MalformedURLException, TypeValidationFailedException_Exception, UserNotFoundException_Exception {
        URL url = new URL("http://localhost:8080/tks-soap/UserService?wsdl");
        var service = new UserService(url);
        var res = service.getUserPort();
        var f = res.getUser("mszewc@op.pl");
    }
}
