package microservices.rental.links

import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class RemoteLinkProperty(
    val uriPath: String,
    val rel: String,
)

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class LinkProperty(
    val resource: KClass<*>,
    val method: String,
    val rel: String,
)