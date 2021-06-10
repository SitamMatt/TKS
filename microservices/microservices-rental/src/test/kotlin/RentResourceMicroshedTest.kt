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
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.microshed.testing.jupiter.MicroShedTest
import org.microshed.testing.kafka.KafkaProducerClient
import org.microshed.testing.testcontainers.ApplicationContainer
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.containers.Network
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import java.time.Duration
import java.util.*
import org.testcontainers.shaded.com.google.common.collect.ImmutableMap
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.common.serialization.StringSerializer
import org.testcontainers.utility.DockerImageName


@MicroShedTest
class RentResourceMicroshedTest {

    class PostgresContainer(imageName: String) : PostgreSQLContainer<PostgresContainer>(imageName)


    companion object {
        private val network: Network = Network.newNetwork()

//        @KafkaProducerClient()
//        @JvmField
//        var producer: KafkaProducer<String?, String?>? = null

        @Container
        @JvmField
        var kafka = KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:5.4.3"))
            .withNetwork(network)
            .withNetworkAliases("kafka")

        @Container
        @JvmField
        val app: ApplicationContainer = ApplicationContainer()
            .withEnv("KAFKA_BROKER", "kafka:9092")
            .withReadinessPath("/health")
            .withHttpPort(8090)
            .withAppContextRoot("/rent-service/api")
            .withStartupTimeout(Duration.ofMinutes(1))
            .dependsOn(kafka)
            .withNetwork(network)
            .withExposedPorts(8090, 9009)


        @Container
        @JvmField
        val db: PostgresContainer = PostgresContainer("postgres:13")
            .withUsername("postgres")
            .withDatabaseName("rentalDB")
            .withPassword("postgres")
            .withExposedPorts(5432)
            .withNetworkAliases("db")
            .withNetwork(network)
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
    fun kafkaDeleteTest(){
        val producer: KafkaProducer<String, String> = KafkaProducer(
            ImmutableMap.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.bootstrapServers,
                ProducerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString()
            ) as Map<String, Any>?,
            StringSerializer(),
            StringSerializer()
        )
        producer.send(
            ProducerRecord(
                "products",
                "NJFH-494",
                "{\"accessionNumber\":\"NJFH-494\",\"locked\":false,\"title\":\"Elantris\",\"author\":\"Brandon Sanderson\",\"type\":\"BOOK\",\"deleted\":false}"
            )
        ).get()
        Thread.sleep(10000)
        producer.send(
            ProducerRecord(
                "products",
                "NJFH-494-deleted",
                "{\"accessionNumber\":\"NJFH-494\",\"locked\":false,\"title\":\"Elantris\",\"author\":\"Brandon Sanderson\",\"type\":\"BOOK\",\"deleted\":true}"
            )
        ).get()
        Thread.sleep(20000)
        val response = Given {
            filter(ResponseLoggingFilter.logResponseTo(System.out))
            pathParam("productId", "NJFH-494")
        } When {
            post("rent/{productId}")
        } Then {
            statusCode(500)
        }
    }

    @Test
    fun kafkaCreateTest() {
        val producer: KafkaProducer<String, String> = KafkaProducer(
            ImmutableMap.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.bootstrapServers,
                ProducerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString()
            ) as Map<String, Any>?,
            StringSerializer(),
            StringSerializer()
        )
        producer.send(
            ProducerRecord(
                "products",
                "NJFH-494",
                "{\"accessionNumber\":\"NJFH-494\",\"locked\":false,\"title\":\"Elantris\",\"author\":\"Brandon Sanderson\",\"type\":\"BOOK\",\"deleted\":false}"
            )
        ).get()
        producer.flush()
        Thread.sleep(10000)
        val response = Given {
            filter(ResponseLoggingFilter.logResponseTo(System.out))
            pathParam("productId", "NJFH-494")
        } When {
            post("rent/{productId}")
        } Then {
            statusCode(201)
        }

        val rentLink = response.extract().header("Location")
        val rentProductId = response.extract().path<String>("productId")
        val rentId = response.extract().path<String>("id")

        rentLink shouldNotBe null
        rentProductId shouldBe "NJFH-494"
        rentId shouldNotBe null
    }

    @Test
    fun teg() {
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
    fun `put(guid) should finish the rental, when active rent guid is given`() {
        val response = Given {
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