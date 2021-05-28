package microservices.rental.links

import kotlin.reflect.KProperty1
import kotlin.reflect.jvm.javaField

inline fun <reified A : Annotation> KProperty1<*, *>.hasAnnotation(): Boolean {
    return this.javaField?.isAnnotationPresent(A::class.java) ?: false
}

inline fun <reified A : Annotation> KProperty1<*, *>.findAnnotation(): A? {
    return this.javaField?.getAnnotation(A::class.java)
}