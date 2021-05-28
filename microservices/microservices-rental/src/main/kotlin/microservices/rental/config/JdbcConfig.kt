package microservices.rental.config

import javax.annotation.sql.DataSourceDefinition

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