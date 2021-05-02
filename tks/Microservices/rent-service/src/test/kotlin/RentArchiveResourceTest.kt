import microservices.common.error.ErrorDto
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


class RentArchiveResourceTest {
    @Test
    fun `Given valid rent id should return rent`() {
        val response = RestAssured.given()
            .port(8080)
            .baseUri("http://localhost/rent-service/data/rent")
            .contentType(ContentType.JSON)
            .get("/7b4399fe-5f73-40fe-90a4-1163f3dfc221")

        response.then().statusCode(200)
        response.then().body("id", containsString("7b4399fe-5f73-40fe-90a4-1163f3dfc221"))
        response.then().body("resourceAccessionNumber", containsString("EEEE-254"))
        response.then().body("userEmail", containsString("mszewc@edu.pl"))
    }

    @Test
    fun `Given not correct rent id should return NOT FOUND`() {
        val expected = ErrorDto("Rent does not exist", 3)

        val response = RestAssured.given()
            .port(8080)
            .baseUri("http://localhost/rent-service/data/rent")
            .contentType(ContentType.JSON)
            .get("/7b4399fe-1234-40fe-90a4-1163f3dfc221")

        response.then().statusCode(404)
        val body = response.body.`as`<ErrorDto>(ErrorDto::class.java)
        Assertions.assertEquals(expected, body)
    }
}