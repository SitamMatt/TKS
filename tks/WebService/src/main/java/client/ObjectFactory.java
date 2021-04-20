
package client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Role_QNAME = new QName("http://superbiz.org/schema/1.0", "role");
    private final static QName _GetUserResponse_QNAME = new QName("http://superbiz.org/wsdl", "getUserResponse");
    private final static QName _UserNotFoundException_QNAME = new QName("http://superbiz.org/wsdl", "UserNotFoundException");
    private final static QName _GetUser_QNAME = new QName("http://superbiz.org/wsdl", "getUser");
    private final static QName _TypeValidationFailedException_QNAME = new QName("http://superbiz.org/wsdl", "TypeValidationFailedException");
    private final static QName _UserSoapDto_QNAME = new QName("http://superbiz.org/wsdl", "userSoapDto");
    private final static QName _Active_QNAME = new QName("http://superbiz.org/schema/1.0", "active");
    private final static QName _Email_QNAME = new QName("http://superbiz.org/schema/1.0", "email");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UserNotFoundException }
     * 
     */
    public UserNotFoundException createUserNotFoundException() {
        return new UserNotFoundException();
    }

    /**
     * Create an instance of {@link GetUserResponse }
     * 
     */
    public GetUserResponse createGetUserResponse() {
        return new GetUserResponse();
    }

    /**
     * Create an instance of {@link GetUser }
     * 
     */
    public GetUser createGetUser() {
        return new GetUser();
    }

    /**
     * Create an instance of {@link TypeValidationFailedException }
     * 
     */
    public TypeValidationFailedException createTypeValidationFailedException() {
        return new TypeValidationFailedException();
    }

    /**
     * Create an instance of {@link UserSoapDto }
     * 
     */
    public UserSoapDto createUserSoapDto() {
        return new UserSoapDto();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://superbiz.org/schema/1.0", name = "role")
    public JAXBElement<String> createRole(String value) {
        return new JAXBElement<String>(_Role_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://superbiz.org/wsdl", name = "getUserResponse")
    public JAXBElement<GetUserResponse> createGetUserResponse(GetUserResponse value) {
        return new JAXBElement<GetUserResponse>(_GetUserResponse_QNAME, GetUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://superbiz.org/wsdl", name = "UserNotFoundException")
    public JAXBElement<UserNotFoundException> createUserNotFoundException(UserNotFoundException value) {
        return new JAXBElement<UserNotFoundException>(_UserNotFoundException_QNAME, UserNotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://superbiz.org/wsdl", name = "getUser")
    public JAXBElement<GetUser> createGetUser(GetUser value) {
        return new JAXBElement<GetUser>(_GetUser_QNAME, GetUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TypeValidationFailedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://superbiz.org/wsdl", name = "TypeValidationFailedException")
    public JAXBElement<TypeValidationFailedException> createTypeValidationFailedException(TypeValidationFailedException value) {
        return new JAXBElement<TypeValidationFailedException>(_TypeValidationFailedException_QNAME, TypeValidationFailedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserSoapDto }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://superbiz.org/wsdl", name = "userSoapDto")
    public JAXBElement<UserSoapDto> createUserSoapDto(UserSoapDto value) {
        return new JAXBElement<UserSoapDto>(_UserSoapDto_QNAME, UserSoapDto.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://superbiz.org/schema/1.0", name = "active")
    public JAXBElement<Boolean> createActive(Boolean value) {
        return new JAXBElement<Boolean>(_Active_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://superbiz.org/schema/1.0", name = "email")
    public JAXBElement<String> createEmail(String value) {
        return new JAXBElement<String>(_Email_QNAME, String.class, null, value);
    }

}
