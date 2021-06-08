package microservices.library

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.micronaut.context.ApplicationContext
import io.micronaut.context.env.PropertySource
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.restassured.RestAssured
import io.restassured.RestAssured.config
import io.restassured.config.LogConfig
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DemoTest {


    class KGenericContainer(imageName: String) : PostgreSQLContainer<KGenericContainer>(imageName)

    val application: EmbeddedApplication<*>

    init {
        db.start()
        application =
            ApplicationContext.run(
                EmbeddedServer::class.java,
                PropertySource.of("test", mapOf("datasources.default.url" to db.jdbcUrl))
            )
        RestAssured.baseURI = application.uri.toString()
        RestAssured.port = application.port
        RestAssured.config = config().logConfig(LogConfig.logConfig().enablePrettyPrinting(true))
    }

    companion object {
        @Container
        @JvmField
        val db: KGenericContainer = KGenericContainer("postgres:13")
            .withUsername("postgres")
            .withDatabaseName("libraryDB")
            .withPassword("postgres")
            .withExposedPorts(5432)
    }

    @Test
    fun testItWorks() {
        val rentId: String = Given {
            filter(ResponseLoggingFilter.logResponseTo(System.out))
            pathParam("id", "EEEE-254")
        } When {
            get("library/{id}")
        } Then {
            statusCode(200)
        } Extract {
            path("accessionNumber")
        }

        rentId shouldNotBe null
        rentId shouldBe "EEEE-254"
    }

}
