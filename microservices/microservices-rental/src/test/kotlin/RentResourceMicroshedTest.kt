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

    @BeforeEach
    fun init() {
        RestAssured.config = RestAssuredConfig.config().logConfig(LogConfig.logConfig().enablePrettyPrinting(true))
        id = "0b41fc23-83bb-46d9-a1a1-70eb750482cf"
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
}