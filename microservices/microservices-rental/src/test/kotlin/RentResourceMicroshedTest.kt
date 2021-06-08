import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.restassured.RestAssured
import io.restassured.config.LogConfig
import io.restassured.config.RestAssuredConfig
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.microshed.testing.jupiter.MicroShedTest
import org.microshed.testing.testcontainers.ApplicationContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import java.time.Duration

@MicroShedTest
class RentResourceMicroshedTest {

    class KGenericContainer(imageName: String) : PostgreSQLContainer<KGenericContainer>(imageName)


    companion object{
        @Container
        @JvmField val app = ApplicationContainer()
            .withEnv("KAFKA_BROKER", "host.docker.internal:9093")
            .withReadinessPath("/health")
            .withHttpPort(8090)
            .withAppContextRoot("/rent-service/api")
            .withStartupTimeout(Duration.ofMinutes(1))

        @Container
        @JvmField val db = KGenericContainer("postgres:13")
            .withUsername("postgres")
            .withDatabaseName("rentalDB")
            .withPassword("postgres")
            .withExposedPorts(5432)
            .withNetworkAliases("db")
    }

    private lateinit var id: String
    private lateinit var productId: String

    @BeforeEach
    fun init() {
        RestAssured.config = RestAssuredConfig.config().logConfig(LogConfig.logConfig().enablePrettyPrinting(true))
        id = "0b41fc23-83bb-46d9-a1a1-70eb750482cf"
        productId = "EEEE-254"
    }

    @Test
    fun teg(){
        val rentId: String = Given {
            filter(ResponseLoggingFilter.logResponseTo(System.out))
            pathParam("id", id)
        } When {
            get("rent/{id}")
        } Then {
            statusCode(200)
        } Extract {
            path("id")
        }

        rentId shouldNotBe null
        rentId shouldBe id
    }

    @Test
    fun `post(productId) should return 201 and header to created resource, when available product id is given`() {
        val response = Given {
            filter(ResponseLoggingFilter.logResponseTo(System.out))
            pathParam("productId", productId)
        } When {
            post("rent/{productId}")
        } Then {
            statusCode(201)
        }

        val rentLink = response.extract().header("Location")
        val rentProductId = response.extract().path<String>("productId")
        val rentId = response.extract().path<String>("id")

        rentLink shouldNotBe null
        rentProductId shouldBe productId
        rentId shouldNotBe null
    }

    @Test
    fun `put(guid) should finish the rental, when active rent guid is given`(){
        val response = Given{
            filter(ResponseLoggingFilter.logResponseTo(System.out))
            pathParam("id", id)
        } When {
            put("/rent/{id}/return")
        } Then {
            statusCode(200)
        }

        val rentEndDate = response.extract().path<String>("endDate")

        rentEndDate shouldNotBe null
        response.extract().path<String>("clientId") shouldBe "mszewc@edu.pl"
        response.extract().path<String>("id") shouldBe id
    }
}