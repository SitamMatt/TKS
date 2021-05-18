package microservices.user.management

import io.quarkus.scheduler.Scheduled
import org.eclipse.microprofile.reactive.messaging.Outgoing
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class ExampleProducer {

//    @Scheduled(every = "10s")
//    fun scheduledFun(){
//        send()
//    }

//    @Outgoing("prices-out")
//    fun send(): String {
//        return "Hello from Quarkus"
//    }
}