
package client.libraryitem;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.FaultAction;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "ILibraryItemWebService", targetNamespace = "http://superbiz.org/wsdl")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface ILibraryItemWebService {


    /**
     * 
     * @param arg0
     * @return
     *     returns client.libraryitem.LibraryItemSoapDto
     * @throws ResourceNotFoundException_Exception
     * @throws TypeValidationFailedException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getLibraryItem", targetNamespace = "http://superbiz.org/wsdl", className = "client.libraryitem.GetLibraryItem")
    @ResponseWrapper(localName = "getLibraryItemResponse", targetNamespace = "http://superbiz.org/wsdl", className = "client.libraryitem.GetLibraryItemResponse")
    @Action(input = "http://superbiz.org/wsdl/ILibraryItemWebService/getLibraryItemRequest", output = "http://superbiz.org/wsdl/ILibraryItemWebService/getLibraryItemResponse", fault = {
        @FaultAction(className = TypeValidationFailedException_Exception.class, value = "http://superbiz.org/wsdl/ILibraryItemWebService/getLibraryItem/Fault/TypeValidationFailedException"),
        @FaultAction(className = ResourceNotFoundException_Exception.class, value = "http://superbiz.org/wsdl/ILibraryItemWebService/getLibraryItem/Fault/ResourceNotFoundException")
    })
    public LibraryItemSoapDto getLibraryItem(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0)
        throws ResourceNotFoundException_Exception, TypeValidationFailedException_Exception
    ;

}