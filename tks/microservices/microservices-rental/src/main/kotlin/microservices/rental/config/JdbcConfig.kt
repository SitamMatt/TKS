package microservices.rental.config

import javax.annotation.sql.DataSourceDefinition
import javax.enterprise.context.ApplicationScoped
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@DataSourceDefinition(
    name ="java:app/jdbc/rental",
    className = "org.h2.jdbcx.JdbcDataSource",
    user = "sa",
    password = "",
    url = "jdbc:h2:tcp://localhost/~/test"
)
//@DataSourceDefinition(
//    name ="java:app/jdbc/rental",
//    className = "org.h2.jdbcx.JdbcDataSource",
//    user = "sa",
//    password = "",
//    url = "jdbc:h2:mem:ExampleRest"
//)
class JdbcConfig