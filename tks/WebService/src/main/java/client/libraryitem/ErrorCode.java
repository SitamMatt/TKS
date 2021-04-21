
package client.libraryitem;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for errorCode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="errorCode">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="UserNotFound"/>
 *     &lt;enumeration value="ResourceNotFound"/>
 *     &lt;enumeration value="RentDoesNotExist"/>
 *     &lt;enumeration value="ResourceRented"/>
 *     &lt;enumeration value="InactiveUser"/>
 *     &lt;enumeration value="DuplicatedEmail"/>
 *     &lt;enumeration value="InvalidResourceFormat"/>
 *     &lt;enumeration value="ResourceInUse"/>
 *     &lt;enumeration value="InvalidIdentifierFormat"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "errorCode")
@XmlEnum
public enum ErrorCode {

    @XmlEnumValue("UserNotFound")
    USER_NOT_FOUND("UserNotFound"),
    @XmlEnumValue("ResourceNotFound")
    RESOURCE_NOT_FOUND("ResourceNotFound"),
    @XmlEnumValue("RentDoesNotExist")
    RENT_DOES_NOT_EXIST("RentDoesNotExist"),
    @XmlEnumValue("ResourceRented")
    RESOURCE_RENTED("ResourceRented"),
    @XmlEnumValue("InactiveUser")
    INACTIVE_USER("InactiveUser"),
    @XmlEnumValue("DuplicatedEmail")
    DUPLICATED_EMAIL("DuplicatedEmail"),
    @XmlEnumValue("InvalidResourceFormat")
    INVALID_RESOURCE_FORMAT("InvalidResourceFormat"),
    @XmlEnumValue("ResourceInUse")
    RESOURCE_IN_USE("ResourceInUse"),
    @XmlEnumValue("InvalidIdentifierFormat")
    INVALID_IDENTIFIER_FORMAT("InvalidIdentifierFormat");
    private final String value;

    ErrorCode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ErrorCode fromValue(String v) {
        for (ErrorCode c: ErrorCode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
