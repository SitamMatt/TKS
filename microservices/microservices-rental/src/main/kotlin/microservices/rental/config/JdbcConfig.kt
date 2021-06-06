package microservices.rental.config

import javax.annotation.sql.DataSourceDefinition

//@DataSourceDefinition(
//    name ="java:app/jdbc/rental",
//    className = "org.h2.jdbcx.JdbcDataSource",
//    user = "sa",
//    password = "",
//    url = "jdbc:h2:tcp://127.0.0.1/~/test"
//)
//@DataSourceDefinition(
//    name ="java:app/jdbc/rental",
//    className = "org.h2.jdbcx.JdbcDataSource",
//    user = "sa",
//    password = "",
//    url = "jdbc:h2:tcp://host.docker.internal/~/test"
//)
//@DataSourceDefinition(
//    name ="java:app/jdbc/rental",
//    className = "org.h2.jdbcx.JdbcDataSource",
//    user = "sa",
//    password = "",
//    url = "jdbc:h2:mem:ExampleRest"
//)
//@DataSourceDefinition(
//    name = "java:app/jdbc/rentalDS",
//    className = "\${MPCONFIG=db.class_name}",
//    user = "\${MPCONFIG=db.user}",
//    password = "\${MPCONFIG=db.password}",
//    databaseName = "\${MPCONFIG=db.database_name",
//    serverName = "\${MPCONFIG=db.server_name}",
//    properties = [
//        "portNumber=\${MPCONFIG=db.port_number}"
//    ]
//)
class JdbcConfig