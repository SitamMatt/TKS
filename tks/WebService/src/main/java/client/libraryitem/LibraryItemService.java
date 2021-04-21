
package client.libraryitem;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "LibraryItemService", targetNamespace = "http://superbiz.org/wsdl", wsdlLocation = "http://localhost:8080/tks-soap/LibraryItemService?wsdl")
public class LibraryItemService
    extends Service
{

    private final static URL LIBRARYITEMSERVICE_WSDL_LOCATION;
    private final static WebServiceException LIBRARYITEMSERVICE_EXCEPTION;
    private final static QName LIBRARYITEMSERVICE_QNAME = new QName("http://superbiz.org/wsdl", "LibraryItemService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8080/tks-soap/LibraryItemService?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        LIBRARYITEMSERVICE_WSDL_LOCATION = url;
        LIBRARYITEMSERVICE_EXCEPTION = e;
    }

    public LibraryItemService() {
        super(__getWsdlLocation(), LIBRARYITEMSERVICE_QNAME);
    }

    public LibraryItemService(WebServiceFeature... features) {
        super(__getWsdlLocation(), LIBRARYITEMSERVICE_QNAME, features);
    }

    public LibraryItemService(URL wsdlLocation) {
        super(wsdlLocation, LIBRARYITEMSERVICE_QNAME);
    }

    public LibraryItemService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, LIBRARYITEMSERVICE_QNAME, features);
    }

    public LibraryItemService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public LibraryItemService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns ILibraryItemWebService
     */
    @WebEndpoint(name = "LibraryItemPort")
    public ILibraryItemWebService getLibraryItemPort() {
        return super.getPort(new QName("http://superbiz.org/wsdl", "LibraryItemPort"), ILibraryItemWebService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ILibraryItemWebService
     */
    @WebEndpoint(name = "LibraryItemPort")
    public ILibraryItemWebService getLibraryItemPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://superbiz.org/wsdl", "LibraryItemPort"), ILibraryItemWebService.class, features);
    }

    private static URL __getWsdlLocation() {
        if (LIBRARYITEMSERVICE_EXCEPTION!= null) {
            throw LIBRARYITEMSERVICE_EXCEPTION;
        }
        return LIBRARYITEMSERVICE_WSDL_LOCATION;
    }

}
