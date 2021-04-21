
package client.rent;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the client.rent package. 
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

    private final static QName _RentSoapDto_QNAME = new QName("http://superbiz.org/wsdl", "rentSoapDto");
    private final static QName _RentNotFoundException_QNAME = new QName("http://superbiz.org/wsdl", "RentNotFoundException");
    private final static QName _EndDate_QNAME = new QName("http://superbiz.org/schema/1.0", "endDate");
    private final static QName _GetRent_QNAME = new QName("http://superbiz.org/wsdl", "getRent");
    private final static QName _GetRentResponse_QNAME = new QName("http://superbiz.org/wsdl", "getRentResponse");
    private final static QName _Id_QNAME = new QName("http://superbiz.org/schema/1.0", "id");
    private final static QName _UserEmail_QNAME = new QName("http://superbiz.org/schema/1.0", "userEmail");
    private final static QName _StartDate_QNAME = new QName("http://superbiz.org/schema/1.0", "startDate");
    private final static QName _ResourceAccessionNumber_QNAME = new QName("http://superbiz.org/schema/1.0", "resourceAccessionNumber");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: client.rent
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RentSoapDto }
     * 
     */
    public RentSoapDto createRentSoapDto() {
        return new RentSoapDto();
    }

    /**
     * Create an instance of {@link RentNotFoundException }
     * 
     */
    public RentNotFoundException createRentNotFoundException() {
        return new RentNotFoundException();
    }

    /**
     * Create an instance of {@link GetRent }
     * 
     */
    public GetRent createGetRent() {
        return new GetRent();
    }

    /**
     * Create an instance of {@link GetRentResponse }
     * 
     */
    public GetRentResponse createGetRentResponse() {
        return new GetRentResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RentSoapDto }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://superbiz.org/wsdl", name = "rentSoapDto")
    public JAXBElement<RentSoapDto> createRentSoapDto(RentSoapDto value) {
        return new JAXBElement<RentSoapDto>(_RentSoapDto_QNAME, RentSoapDto.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RentNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://superbiz.org/wsdl", name = "RentNotFoundException")
    public JAXBElement<RentNotFoundException> createRentNotFoundException(RentNotFoundException value) {
        return new JAXBElement<RentNotFoundException>(_RentNotFoundException_QNAME, RentNotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://superbiz.org/schema/1.0", name = "endDate")
    public JAXBElement<XMLGregorianCalendar> createEndDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_EndDate_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://superbiz.org/wsdl", name = "getRent")
    public JAXBElement<GetRent> createGetRent(GetRent value) {
        return new JAXBElement<GetRent>(_GetRent_QNAME, GetRent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://superbiz.org/wsdl", name = "getRentResponse")
    public JAXBElement<GetRentResponse> createGetRentResponse(GetRentResponse value) {
        return new JAXBElement<GetRentResponse>(_GetRentResponse_QNAME, GetRentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://superbiz.org/schema/1.0", name = "id")
    public JAXBElement<String> createId(String value) {
        return new JAXBElement<String>(_Id_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://superbiz.org/schema/1.0", name = "userEmail")
    public JAXBElement<String> createUserEmail(String value) {
        return new JAXBElement<String>(_UserEmail_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://superbiz.org/schema/1.0", name = "startDate")
    public JAXBElement<XMLGregorianCalendar> createStartDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_StartDate_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://superbiz.org/schema/1.0", name = "resourceAccessionNumber")
    public JAXBElement<String> createResourceAccessionNumber(String value) {
        return new JAXBElement<String>(_ResourceAccessionNumber_QNAME, String.class, null, value);
    }

}
