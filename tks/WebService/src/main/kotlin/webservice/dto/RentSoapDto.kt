package webservice.dto

import domain.model.values.AccessionNumber
import domain.model.values.Email
import java.util.*
import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class RentSoapDto {
    @XmlElement(namespace = "http://superbiz.org/schema/1.0")
    var id: UUID? = null

    @XmlElement(namespace = "http://superbiz.org/schema/1.0")
    var startDate: Date? = null

    @XmlElement(namespace = "http://superbiz.org/schema/1.0")
    var endDate: Date? = null

    @XmlElement(namespace = "http://superbiz.org/schema/1.0")
    var userEmail: String? = null

    @XmlElement(namespace = "http://superbiz.org/schema/1.0")
    var resourceAccessionNumber: String? = null
}