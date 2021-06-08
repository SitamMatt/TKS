package microservices.library

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.micronaut.context.ApplicationContext
import io.micronaut.context.env.PropertySource
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.runtime.server.EmbeddedServer
import io.restassured.RestAssured
import io.restassured.RestAssured.config
import io.restassured.config.LogConfig
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import microservices.library.dto.LibraryResourceDto
import microservices.library.dto.LibraryResourceType
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.utility.DockerImageName
import java.net.URI

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DemoTest {


    class KGenericContainer(imageName: String) : PostgreSQLContainer<KGenericContainer>(imageName)

    val application: EmbeddedApplication<*>

    init {
        db.start()
        kafka.start()
        val url = URI(kafka.bootstrapServers)
        val bootstrapServerUrl = "${url.host}:${url.port}"
        application =
            ApplicationContext.run(
                EmbeddedServer::class.java,
                PropertySource.of(
                    "test", mapOf(
                        "datasources.default.url" to db.jdbcUrl,
                        "kafka.bootstrap.servers" to bootstrapServerUrl
                    )
                )
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

        @Container
        @JvmField
        val kafka = KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:5.4.3"))
    }

    @Test
    fun getProperTest() {
        val response = Given {
            filter(ResponseLoggingFilter.logResponseTo(System.out))
            pathParam("id", "EEEE-254")
        } When {
            get("library/{id}")
        } Then {
            statusCode(200)
        }

        response.extract().path<String>("accessionNumber") shouldNotBe null
        response.extract().path<String>("accessionNumber") shouldBe "EEEE-254"
        response.extract().path<String>("title") shouldBe "Dune"
        response.extract().path<String>("author") shouldBe "Frank Herbert"
        response.extract().path<String>("type") shouldBe "BOOK"
    }

    @Test
    fun postProperTest() {
        val model = LibraryResourceDto(null, false, "Elantris", "Brandon Sanderson", "MAG", LibraryResourceType.BOOK)
        val number: String = Given{
            filter(ResponseLoggingFilter.logResponseTo(System.out))
            contentType(ContentType.JSON)
            body(model)
        } When {
            post("library")
        } Then {
            statusCode(201)
        } Extract {
            path<String>("accessionNumber")
        }

        val response2 = Given {
            filter(ResponseLoggingFilter.logResponseTo(System.out))
            pathParam("id", number)
        } When {
            get("library/{id}")
        } Then {
            statusCode(200)
        }

        response2.extract().path<String>("title") shouldBe "Elantris"
        response2.extract().path<String>("author") shouldBe "Brandon Sanderson"
        response2.extract().path<String>("type") shouldBe "BOOK"
    }

    @Test
    fun deleteProperTest() {
        val response = Given {
            filter(ResponseLoggingFilter.logResponseTo(System.out))
            pathParam("id", "EEEE-254")
        } When {
            delete("library/{id}")
        } Then {
            statusCode(200)
        }

        val response2 = Given {
            filter(ResponseLoggingFilter.logResponseTo(System.out))
            pathParam("id", "EEEE-254")
        } When {
            get("library/{id}")
        } Then {
            statusCode(404)
        }

        response2.extract().path<String>("message") shouldBe "Resource not found"
    }

}
