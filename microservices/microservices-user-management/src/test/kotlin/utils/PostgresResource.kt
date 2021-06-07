package utils

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.PostgreSQLContainer
import java.util.*

class PostgresResource : QuarkusTestResourceLifecycleManager {

    class KGenericContainer(imageName: String) : PostgreSQLContainer<KGenericContainer>(imageName)


    override fun start(): MutableMap<String, String> {
        db.start()
        return Collections.singletonMap("quarkus.datasource.jdbc.url", db.jdbcUrl)
    }

    override fun stop() {
        db.close()
    }

    companion object{
        val db: KGenericContainer = KGenericContainer("postgres:13")
            .withUsername("postgres")
            .withDatabaseName("userDB")
            .withPassword("postgres")
            .withExposedPorts(5432)
    }
}