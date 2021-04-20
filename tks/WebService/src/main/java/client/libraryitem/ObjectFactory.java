
package client.libraryitem;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the client.libraryitem package. 
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

    private final static QName _LibraryItemSoapDto_QNAME = new QName("http://superbiz.org/wsdl", "libraryItemSoapDto");
    private final static QName _GetLibraryItem_QNAME = new QName("http://superbiz.org/wsdl", "getLibraryItem");
    private final static QName _Author_QNAME = new QName("http://superbiz.org/schema/1.0", "author");
    private final static QName _GetLibraryItemResponse_QNAME = new QName("http://superbiz.org/wsdl", "getLibraryItemResponse");
    private final static QName _TypeValidationFailedException_QNAME = new QName("http://superbiz.org/wsdl", "TypeValidationFailedException");
    private final static QName _Publisher_QNAME = new QName("http://superbiz.org/schema/1.0", "publisher");
    private final static QName _AccessionNumber_QNAME = new QName("http://superbiz.org/schema/1.0", "accessionNumber");
    private final static QName _Title_QNAME = new QName("http://superbiz.org/schema/1.0", "title");
    private final static QName _Type_QNAME = new QName("http://superbiz.org/schema/1.0", "type");
    private final static QName _ResourceNotFoundException_QNAME = new QName("http://superbiz.org/wsdl", "ResourceNotFoundException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: client.libraryitem
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetLibraryItem }
     * 
     */
    public GetLibraryItem createGetLibraryItem() {
        return new GetLibraryItem();
    }

    /**
     * Create an instance of {@link LibraryItemSoapDto }
     * 
     */
    public LibraryItemSoapDto createLibraryItemSoapDto() {
        return new LibraryItemSoapDto();
    }

    /**
     * Create an instance of {@link GetLibraryItemResponse }
     * 
     */
    public GetLibraryItemResponse createGetLibraryItemResponse() {
        return new GetLibraryItemResponse();
    }

    /**
     * Create an instance of {@link TypeValidationFailedException }
     * 
     */
    public TypeValidationFailedException createTypeValidationFailedException() {
        return new TypeValidationFailedException();
    }

    /**
     * Create an instance of {@link ResourceNotFoundException }
     * 
     */
    public ResourceNotFoundException createResourceNotFoundException() {
        return new ResourceNotFoundException();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LibraryItemSoapDto }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://superbiz.org/wsdl", name = "libraryItemSoapDto")
    public JAXBElement<LibraryItemSoapDto> createLibraryItemSoapDto(LibraryItemSoapDto value) {
        return new JAXBElement<LibraryItemSoapDto>(_LibraryItemSoapDto_QNAME, LibraryItemSoapDto.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLibraryItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://superbiz.org/wsdl", name = "getLibraryItem")
    public JAXBElement<GetLibraryItem> createGetLibraryItem(GetLibraryItem value) {
        return new JAXBElement<GetLibraryItem>(_GetLibraryItem_QNAME, GetLibraryItem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://superbiz.org/schema/1.0", name = "author")
    public JAXBElement<String> createAuthor(String value) {
        return new JAXBElement<String>(_Author_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLibraryItemResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://superbiz.org/wsdl", name = "getLibraryItemResponse")
    public JAXBElement<GetLibraryItemResponse> createGetLibraryItemResponse(GetLibraryItemResponse value) {
        return new JAXBElement<GetLibraryItemResponse>(_GetLibraryItemResponse_QNAME, GetLibraryItemResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://superbiz.org/schema/1.0", name = "publisher")
    public JAXBElement<String> createPublisher(String value) {
        return new JAXBElement<String>(_Publisher_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://superbiz.org/schema/1.0", name = "accessionNumber")
    public JAXBElement<String> createAccessionNumber(String value) {
        return new JAXBElement<String>(_AccessionNumber_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://superbiz.org/schema/1.0", name = "title")
    public JAXBElement<String> createTitle(String value) {
        return new JAXBElement<String>(_Title_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://superbiz.org/schema/1.0", name = "type")
    public JAXBElement<String> createType(String value) {
        return new JAXBElement<String>(_Type_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResourceNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://superbiz.org/wsdl", name = "ResourceNotFoundException")
    public JAXBElement<ResourceNotFoundException> createResourceNotFoundException(ResourceNotFoundException value) {
        return new JAXBElement<ResourceNotFoundException>(_ResourceNotFoundException_QNAME, ResourceNotFoundException.class, null, value);
    }

}
