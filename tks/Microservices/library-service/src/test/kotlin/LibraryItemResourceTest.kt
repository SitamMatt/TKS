import microservices.common.error.ErrorDto
import io.restassured.RestAssured
import io.restassured.http.ContentType
import it.p.lodz.pl.service.library.dto.LibraryItemDto
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class LibraryItemResourceTest {

    @Test
    fun `Given valid resource id should return resource` (){
        val expected = LibraryItemDto("EEEE-254", "Diuna", "Frank Herbert", null, "BOOK")

        val response = RestAssured.given()
                .port(8080)
                .baseUri("http://localhost/library-service/data/library/item")
                .contentType(ContentType.JSON)
                .get("/EEEE-254")

        response.then().statusCode(200)
        val body = response.body.`as`<LibraryItemDto>(LibraryItemDto::class.java)
        Assertions.assertEquals(expected, body)
    }

    @Test
    fun `Given nonexistent resource id, get should return NOT FOUND`(){
        val expected = ErrorDto("Resource not found", 2)

        val response = RestAssured.given()
                .port(8080)
                .baseUri("http://localhost/library-service/data/library/item")
                .get("/EEEE-943")

        response.then().statusCode(404)
        val body = response.body.`as`<ErrorDto>(ErrorDto::class.java)
        Assertions.assertEquals(expected, body)
    }

    @Test
    fun `Given id in wrong format, get should return Invalid Identifier Format`(){
        val expected = ErrorDto("The given identifier format is invalid", 16)

        val response = RestAssured.given()
                .port(8080)
                .baseUri("http://localhost/library-service/data/library/item")
                .get("/EEE43")

        response.then().statusCode(400)
        val body = response.body.`as`<ErrorDto>(ErrorDto::class.java)
        Assertions.assertEquals(expected, body)
    }

    @Test
    fun `Given valid item resource, post should create new library item resource`(){
        val model = LibraryItemDto(null, "Elantris", "Brandon Sanderson", null, "BOOK")
        val expected = LibraryItemDto(null, "Elantris", "Brandon Sanderson", null, "BOOK")

        val response1 = RestAssured.given()
                .port(8080)
                .baseUri("http://localhost/library-service/data/library/item")
                .contentType(ContentType.JSON)
                .body(model)
                .post()

        response1.then().statusCode(201)

        val header = response1.headers["Location"]
        assertNotNull(header)

        val response2 = RestAssured.given()
                .port(8080)
                .contentType(ContentType.JSON)
                .get(header.value)

        response2.then().statusCode(200)
        val body = response2.body.`as`<LibraryItemDto>(LibraryItemDto::class.java) as LibraryItemDto
        body.accessionNumber = null
        Assertions.assertEquals(expected, body)
    }

    @Test
    fun `Given library item with wrong format or type, post should return BAD REQUEST`() {
        val expected = ErrorDto("The resource format is invalid", 14)
        val model = LibraryItemDto("Elantris", "Brandon Sanderson", "MAG", null, "EBOOK")

        val response = RestAssured.given()
                .port(8080)
                .baseUri("http://localhost/library-service/data/library/item")
                .contentType(ContentType.JSON)
                .body(model)
                .post()

        response.then().statusCode(400)

        val body = response.body.`as`<ErrorDto>(ErrorDto::class.java)
        Assertions.assertEquals(expected, body)
    }
}