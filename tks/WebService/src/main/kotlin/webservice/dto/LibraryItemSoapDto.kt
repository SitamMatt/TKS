package webservice.dto

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
open class LibraryItemSoapDto {
    @XmlElement(namespace = "http://superbiz.org/schema/1.0")
    var accessionNumber: String? = null
    @XmlElement(namespace = "http://superbiz.org/schema/1.0")
    var title: String? = null
    @XmlElement(namespace = "http://superbiz.org/schema/1.0")
    var author: String? = null
    @XmlElement(namespace = "http://superbiz.org/schema/1.0")
    var publisher: String? = null
    @XmlElement(namespace = "http://superbiz.org/schema/1.0")
    var type: String? = null
}