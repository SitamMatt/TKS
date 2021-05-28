import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.restassured.RestAssured
import io.restassured.config.LogConfig
import io.restassured.config.RestAssuredConfig.config
import io.restassured.filter.log.ResponseLoggingFilter.logResponseTo
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import microservices.common.error.ErrorDto
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RentResourceTest {

    private lateinit var id: String
    private lateinit var productId: String

    @BeforeEach
    fun init() {
        RestAssured.baseURI = "http://localhost/api/"
        RestAssured.port = 8080
        RestAssured.config = config().logConfig(LogConfig.logConfig().enablePrettyPrinting(true))
        id = "0b41fc23-83bb-46d9-a1a1-70eb750482cf"
        productId = "EEEE-254"
    }

    @Test
    fun `get(id) should return appropriate rental, when valid guid is given`() {
        val rentId: String = Given {
            filter(logResponseTo(System.out))
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
            filter(logResponseTo(System.out))
            pathParam("productId", productId)
        } When {
            post("rent/{productId}")
        } Then {
            statusCode(201)
        }

        val rentLink = response.extract().header("Location")
        val rentProductId = response.extract().path<String>("productId")

        rentLink shouldNotBe null
        rentProductId shouldBe productId

        // todo ...
    }

    @Test
    fun `put(guid) should finish the rental, when active rent guid is given`(){
        val response = Given{
            filter(logResponseTo(System.out))
            pathParam("id", id)
        } When {
            put("/rent/{id}/return")
        } Then {
            statusCode(200)
        }

        val rentLink = response.extract().header("Location")
        val rentEndDate = response.extract().path<String>("endDate")

        rentLink shouldNotBe null
        rentEndDate shouldNotBe null

        // todo ...
    }

    @Test
    fun `Given valid email and accessing number rent should success`() {
        RestAssured.given()
            .port(8080)
            .baseUri("http://localhost/rent-service/data/library/item/EEEE-154/rent/by/mszewc@edu.pl")
            .post()
            .then()
            .statusCode(201)
    }

    @Test
    fun `Trying to rent resource already rented should return RESOURCE ALREADY RENT`() {
        val expected = ErrorDto("Resource already rented", 11)

        val response = RestAssured.given()
            .port(8080)
            .baseUri("http://localhost/rent-service/data/library/item/EEEE-254/rent/by/mszewc@edu.pl")
            .post()

        response.then().statusCode(409)
        val body = response.body.`as`<ErrorDto>(ErrorDto::class.java)
        Assertions.assertEquals(expected, body)
    }

    @Test
    fun `Given not valid email rent should return NOT FOUND`() {
        val expected = ErrorDto("User not found", 1)
        val response = RestAssured.given()
            .port(8080)
            .baseUri("http://localhost/rent-service/data/library/item/EEEE-214/rent/by/michal@edu.pl")
            .post()

        response.then().statusCode(404)
        val body = response.body.`as`<ErrorDto>(ErrorDto::class.java)
        Assertions.assertEquals(expected, body)
    }

    @Test
    fun `Given not valid accession number rent should return NOT FOUND`() {
        val expected = ErrorDto("Resource not found", 2)

        val response = RestAssured.given()
            .port(8080)
            .baseUri("http://localhost/rent-service/data/library/item/EEEE-214/rent/by/mszewc@edu.pl")
            .post()

        response.then().statusCode(404)
        val body = response.body.`as`<ErrorDto>(ErrorDto::class.java)
        Assertions.assertEquals(expected, body)
    }

    @Test
    fun `Given not active user email rent should return INACTIVE USER`() {
        val expected = ErrorDto("User is inactive", 12)

        val response = RestAssured.given()
            .port(8080)
            .baseUri("http://localhost/rent-service/data/library/item/EEEE-254/rent/by/marcin@edu.pl")
            .post()

        response.then().statusCode(400)
        val body = response.body.`as`<ErrorDto>(ErrorDto::class.java)
        Assertions.assertEquals(expected, body)
    }

    @Test
    fun `Given valid email and accessing number rent delete should success`() {
        RestAssured.given()
            .port(8080)
            .baseUri("http://localhost/rent-service/data/library/item/EEEE-303/rent/by/mszewc@edu.pl")
            .post()
            .then()
            .statusCode(201)

        RestAssured.given()
            .port(8080)
            .baseUri("http://localhost/rent-service/data/library/item/EEEE-303/rent/by/mszewc@edu.pl")
            .delete()
            .then()
            .statusCode(200)
    }

    @Test
    fun `Given not valid email rent delete should return NOT FOUND`() {
        val expected = ErrorDto("User not found", 1)
        val response = RestAssured.given()
            .port(8080)
            .baseUri("http://localhost/rent-service/data/library/item/EEEE-214/rent/by/michal@edu.pl")
            .delete()

        response.then().statusCode(404)
        val body = response.body.`as`<ErrorDto>(ErrorDto::class.java)
        Assertions.assertEquals(expected, body)
    }

    @Test
    fun `Given wrong user email rent delete should return INVALID USER`() {
        val expected = ErrorDto("Rent does not exist", 3)
        val response = RestAssured.given()
            .port(8080)
            .baseUri("http://localhost/rent-service/data/library/item/EEEE-154/rent/by/marcin@edu.pl")
            .delete()

        response.then().statusCode(400)
        val body = response.body.`as`<ErrorDto>(ErrorDto::class.java)
        Assertions.assertEquals(expected, body)
    }

    @Test
    fun `Given not valid accession number rent delete should return NOT FOUND`() {

        val expected = ErrorDto("Resource not found", 2)

        val response = RestAssured.given()
            .port(8080)
            .baseUri("http://localhost/rent-service/data/library/item/EEEE-214/rent/by/mszewc@edu.pl")
            .delete()

        response.then().statusCode(404)
        val body = response.body.`as`<ErrorDto>(ErrorDto::class.java)
        Assertions.assertEquals(expected, body)

    }

    @Test
    fun `Trying to return resource not rented should return RESOURCE NOT RENT`() {
        val expected = ErrorDto("Rent does not exist", 3)

        val response = RestAssured.given()
            .port(8080)
            .baseUri("http://localhost/rent-service/data/library/item/EEEE-303/rent/by/mszewc@edu.pl")
            .delete()

        response.then().statusCode(400)
        val body = response.body.`as`<ErrorDto>(ErrorDto::class.java)
        Assertions.assertEquals(expected, body)
    }
}