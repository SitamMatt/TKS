package webservice.dto

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
open class UserSoapDto{
    @XmlElement(namespace = "http://superbiz.org/schema/1.0")
    var email: String? = null
    @XmlElement(namespace = "http://superbiz.org/schema/1.0")
    var active: Boolean? =null
    @XmlElement(namespace = "http://superbiz.org/schema/1.0")
    var role: String? = null
}