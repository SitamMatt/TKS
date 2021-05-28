package microservices.rental.links

import org.eclipse.microprofile.config.ConfigProvider
import java.util.ArrayList
import javax.ws.rs.core.Link
import javax.ws.rs.core.UriBuilder
import javax.ws.rs.core.UriInfo
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.javaMethod

object LinksResolver {

    inline fun <reified T : Any> resolve(dto: T, target: KMutableProperty0<ArrayList<Link>>, uriInfo: UriInfo) {
        val links = target.get()
        val remoteLinkPropertyList =
            T::class.memberProperties.filter { it.hasAnnotation<RemoteLinkProperty>() }
        for (linkProperty in remoteLinkPropertyList) {
            val annotation = linkProperty.findAnnotation<RemoteLinkProperty>()!!
            val uriConfigPath = annotation.uriPath
            val baseUri = ConfigProvider.getConfig().getValue(uriConfigPath, String::class.java)
            val builder = UriBuilder.fromUri(baseUri)
            val uri = builder.build(linkProperty.get(dto))
            val link = Link.fromUri(uri).rel(annotation.rel).build()
            links.add(link)
        }
        val localLinkPropertyList =
            T::class.memberProperties.filter { it.hasAnnotation<LinkProperty>() }
        for(linkProperty in localLinkPropertyList){
            val annotation = linkProperty.findAnnotation<LinkProperty>()!!
            val resourceClass = annotation.resource
            val method = resourceClass.memberFunctions.find { it.name == annotation.method } ?: continue
            val builder = UriBuilder.fromUri(uriInfo.baseUri)
            builder.path(resourceClass.java)
            builder.path(method.javaMethod)
            val uri = builder.build(linkProperty.get(dto))
            val link = Link.fromUri(uri).rel(annotation.rel).build()
            links.add(link)
        }
    }
}