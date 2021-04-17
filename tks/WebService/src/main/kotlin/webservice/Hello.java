package webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class Hello {

    @WebMethod(operationName = "sayHi")
    public String sayHi(){
        return "hi";
    }
}
