package it.p.lodz.pl.service.rents.config

import javax.annotation.sql.DataSourceDefinition
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@DataSourceDefinition(
    name ="java:app/jdbc/rental",
    className = "org.h2.jdbcx.JdbcDataSource",
    user = "sa",
    password = "",
    url = "jdbc:h2:tcp://localhost/~/test"
)
class JdbcConfig {

    @PersistenceContext(unitName = "rentalPU")
    private lateinit var em: EntityManager
}