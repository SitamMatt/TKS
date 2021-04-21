package rest.api.controllers

import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.hamcrest.Matchers
import org.hamcrest.core.Is
import org.hamcrest.core.StringContains
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import rest.api.dto.ErrorDto
import rest.api.dto.LibraryItemDto
import rest.api.dto.UserDto

class LibraryItemResourceTest {

    @Test
    fun `Given valid resource id should return resource` (){
        val expected = LibraryItemDto("EEEE-254", "Diuna", "Frank Herbert")

        val response = RestAssured.given()
                .port(8080)
                .baseUri("http://localhost/tks/api/library/item")
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
                .baseUri("http://localhost/tks/api/library/item")
                .get("/EEE-943")

        response.then().statusCode(404)
        val body = response.body.`as`<ErrorDto>(ErrorDto::class.java)
        Assertions.assertEquals(expected, body)
    }

    @Test
    fun `Given id in wrong format, get should return Invalid Identifier Format`(){
        val expected = ErrorDto("The given identifier format is invalid", 16)

        val response = RestAssured.given()
                .port(8080)
                .baseUri("http://localhost/tks/api/library/item")
                .get("/EE43")

        response.then().statusCode(400)
        val body = response.body.`as`<ErrorDto>(ErrorDto::class.java)
        Assertions.assertEquals(expected, body)
    }

    @Test
    fun `Given valid item resource, post should create new library item resource`(){
        val model = LibraryItemDto("EEE-154", "Elantris", "Brandon Sanderson", "MAG")
        val expected = LibraryItemDto("EEE-154", "Elantris", "Brandon Sanderson", "MAG")

        RestAssured.given()
                .port(8080)
                .baseUri("http://localhost/tks/api/library/item")
                .contentType(ContentType.JSON)
                .body(model)
                .post()
                .then()
                .statusCode(201)

        val response = RestAssured.given()
                .port(8080)
                .baseUri("http://localhost/tks/api/library/item")
                .contentType(ContentType.JSON)
                .get("/EEE-154")

        response.then().statusCode(200)
        val body = response.body.`as`<LibraryItemDto>(LibraryItemDto::class.java)
        Assertions.assertEquals(expected, body)
    }

    @Test
    fun `Given library item with wrong format, post should return BAD REQUEST`() {
        val expected = ErrorDto("The resource format is invalid", 14)
        val model = LibraryItemDto("Elantris", "Brandon Sanderson", "MAG")

        val response = RestAssured.given()
                .port(8080)
                .baseUri("http://localhost/tks/api/library/item")
                .contentType(ContentType.JSON)
                .body(model)
                .post()

        response.then().statusCode(400)

        val body = response.body.`as`<ErrorDto>(ErrorDto::class.java)
        Assertions.assertEquals(expected, body)
    }
}