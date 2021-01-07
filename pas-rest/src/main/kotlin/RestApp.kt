import javax.annotation.security.DeclareRoles
import javax.ws.rs.ApplicationPath
import javax.ws.rs.core.Application


@DeclareRoles( "ADMIN", "WORKER", "CLIENT")
@ApplicationPath("api")
class RestApp : Application() {
}