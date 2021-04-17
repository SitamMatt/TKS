package webservice.client;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class HelloTest {

    @Test
    public void test() throws MalformedURLException {
        URL url = new URL("http://localhost:8080/tks-soap/HelloService?wsdl");
        var service = new HelloService(url);
        var res = service.getHelloPort();
        var f = res.sayHi();
    }
}
