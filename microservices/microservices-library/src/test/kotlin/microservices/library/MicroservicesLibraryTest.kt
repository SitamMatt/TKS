package microservices.library

import io.kotest.matchers.ints.shouldBeGreaterThan
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
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import microservices.library.dto.LibraryResourceDto
import microservices.library.dto.LibraryResourceType
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.shaded.com.google.common.collect.ImmutableMap
import org.testcontainers.utility.DockerImageName
import java.net.URI
import java.time.Duration
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MicroservicesLibraryTest {


    class KGenericContainer(imageName: String) : PostgreSQLContainer<KGenericContainer>(imageName)

    val application: EmbeddedApplication<*>

    init {
        db.start()
        kafka.start()
        val url = URI(kafka.bootstrapServers)
        bootstrapServerUrl = "${url.host}:${url.port}"
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

        var bootstrapServerUrl: String? = null
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
        val consumer: KafkaConsumer<String, String> = KafkaConsumer(
            ImmutableMap.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServerUrl!!,
                ConsumerConfig.GROUP_ID_CONFIG, "tc-" + UUID.randomUUID(),
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"
            ) as Map<String, Any>?,
            StringDeserializer(),
            StringDeserializer()
        )
        consumer.subscribe(Collections.singletonList("products"))
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
            path("accessionNumber")
        }
        Thread.sleep(5000)
        val records = consumer.poll(Duration.ofMillis(2000))
        records.count() shouldBeGreaterThan 0

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
        Given {
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

    @Test
    fun putProperTest(){
        val consumer: KafkaConsumer<String, String> = KafkaConsumer(
                ImmutableMap.of(
                        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServerUrl!!,
                        ConsumerConfig.GROUP_ID_CONFIG, "tc-" + UUID.randomUUID(),
                        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"
                ) as Map<String, Any>?,
                StringDeserializer(),
                StringDeserializer()
        )
        consumer.subscribe(Collections.singletonList("products"))
        val model = LibraryResourceDto(null, false, "Endymion", "Dan Simmons", "MAG", LibraryResourceType.BOOK)
        val number: String = Given{
            filter(ResponseLoggingFilter.logResponseTo(System.out))
            contentType(ContentType.JSON)
            body(model)
        } When {
            post("library")
        } Then {
            statusCode(201)
        } Extract {
            path("accessionNumber")
        }
        Thread.sleep(5000)
        val records = consumer.poll(Duration.ofMillis(2000))
        records.count() shouldBeGreaterThan 0

        val response2 = Given {
            filter(ResponseLoggingFilter.logResponseTo(System.out))
            pathParam("id", number)
        } When {
            get("library/{id}")
        } Then {
            statusCode(200)
        }

        println(response2)
        response2.extract().path<String>("title") shouldBe "Endymion"
        response2.extract().path<String>("author") shouldBe "Dan Simmons"
        response2.extract().path<String>("type") shouldBe "BOOK"

        val modelEdited = LibraryResourceDto(number, false, "Upadek Hyperiona", "Dan Simmons", "MAG", LibraryResourceType.BOOK)
        val numberEdited: String = Given{
            filter(ResponseLoggingFilter.logResponseTo(System.out))
            contentType(ContentType.JSON)
            body(model)
        } When {
            put("library/{id}")
        } Then {
            statusCode(201)
        } Extract {
            path("accessionNumber")
        }

        val response3 = Given {
            filter(ResponseLoggingFilter.logResponseTo(System.out))
            pathParam("id", numberEdited)
        } When {
            get("library/{id}")
        } Then {
            statusCode(200)
        }

        response2.extract().path<String>("title") shouldBe "Upadek Hyperiona"
        response2.extract().path<String>("author") shouldBe "Dan Simmons"
        response2.extract().path<String>("type") shouldBe "BOOK"
    }

}
