package microservices.rental

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition
import org.eclipse.microprofile.openapi.annotations.info.Info
import javax.ws.rs.ApplicationPath
import javax.ws.rs.core.Application

@OpenAPIDefinition(
    info = Info(
        title = "Rental API application",
        version = "1.0.0",
    )
)
@ApplicationPath("/api")
open class RentalServiceApplication : Application()