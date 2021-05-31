//package microservices.rental.mappers
//
//import javax.enterprise.context.ApplicationScoped
//import javax.enterprise.event.Observes
//import javax.enterprise.inject.literal.InjectLiteral
//import javax.enterprise.inject.spi.*
//
//
//open class ELOEXTENSIONS : Extension {
//
//
//    fun afterBean(@Observes afterBeanDiscovery: AfterBeanDiscovery, bm: BeanManager) {
//        afterBeanDiscovery
//            .addBean<ELO>()
//            .read(bm.createAnnotatedType(ELO::class.java))
//            .beanClass(ELO::class.java)
//            .scope(ApplicationScoped::class.java)
//    }
//
////    open fun addLegacyServiceAsBean(@Observes bbd: BeforeBeanDiscovery) {
////        bbd.addAnnotatedType(ExampleClass::class.java, ExampleClass::class.java.name)
////            .add(ApplicationScoped.Literal.INSTANCE)
////            .filterConstructors { c -> c.getParameters().size() === 1 }
////            .findFirst().get().add(InjectLiteral.INSTANCE)
////    }
//
//    fun <X> processAnnotatedType(@Observes event:  ProcessAnnotatedType<X> ){
//        val at: AnnotatedType<X> = event.annotatedType
//        if(at.javaClass == ExampleClass::class.java){
//            val const = event.configureAnnotatedType().filterConstructors{x -> x?.parameters?.size == 1}
//                .findFirst().get().add(InjectLiteral.INSTANCE)
//        }
//        return
//    }
//
//    fun <X> processInjectionTarget(@Observes pit:  ProcessInjectionTarget<X> ){
//        val it: InjectionTarget<X> = pit.getInjectionTarget()
//    }
//
//    fun <T, X> processInjectionTarget(@Observes pit:  ProcessInjectionPoint<T, X> ){
////        if(pit.)
////        if(pit.injectionPoint.bean.beanClass == ExampleClass::class.java){
////            val it = pit.injectionPoint;
////        }
//    }
//}