package microservices.rental

import javax.ws.rs.ApplicationPath
import javax.ws.rs.core.Application

@ApplicationPath("/api")
open class RentalServiceApplication : Application()