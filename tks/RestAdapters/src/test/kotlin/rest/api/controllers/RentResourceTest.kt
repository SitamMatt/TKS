package rest.api.controllers

import io.restassured.RestAssured
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import rest.api.dto.ErrorDto

class RentResourceTest {
    @Test
    fun `Given valid email and accessing number rent should success`() {
        RestAssured.given()
                .port(8080)
                .baseUri("http://localhost/tks/api/library/item/EEEE-154/rent")
                .post()
                .then()
                .statusCode(200)
    }

    @Test
    fun `Trying to rent resource already rented should return RESOURCE ALREADY RENT`(){
        val expected = ErrorDto("Resource already rented", 11)

        val response = RestAssured.given()
                .port(8080)
                .baseUri("http://localhost/tks/api/library/item/EEEE-254/rent")
                .post()

        response.then().statusCode(409)
        val body = response.body.`as`<ErrorDto>(ErrorDto::class.java)
        Assertions.assertEquals(expected, body)
    }

//    @Test
//    fun `Given not valid email rent should return NOT FOUND`(){
//        val expected = ErrorDto("User not found", 1)
//    }

    @Test
    fun `Given not valid accession number rent should return NOT FOUND`(){
        val expected = ErrorDto("Resource not found", 2)

        val response = RestAssured.given()
                .port(8080)
                .baseUri("http://localhost/tks/api/library/item/EEEE-214/rent")
                .post()

        response.then().statusCode(404)
        val body = response.body.`as`<ErrorDto>(ErrorDto::class.java)
        Assertions.assertEquals(expected, body)
    }

//    @Test
//    fun `Given not active user email rent should return INACTIVE USER`(){
//        val expected = ErrorDto("User is inactive", 12)
//    }

    @Test
    fun `Given valid email and accessing number rent delete should success`(){
        RestAssured.given()
                .port(8080)
                .baseUri("http://localhost/tks/api/library/item/EEEE-254/rent")
                .delete()
                .then()
                .statusCode(200)
    }

//    @Test
//    fun `Given not valid email rent delete should return NOT FOUND`(){
//        val expected = ErrorDto("User not found", 1)
//    }

//    @Test
//    fun `Given wrong user email rent delete should return INVALID USER`(){
//        val expected = ErrorDto("Rent does not exist", 3)
//    }

    @Test
    fun `Given not valid accession number rent delete should return NOT FOUND`(){

        val expected = ErrorDto("Resource not found", 2)

        val response = RestAssured.given()
                .port(8080)
                .baseUri("http://localhost/tks/api/library/item/EEEE-214/rent")
                .delete()

        response.then().statusCode(404)
        val body = response.body.`as`<ErrorDto>(ErrorDto::class.java)
        Assertions.assertEquals(expected, body)

    }

    @Test
    fun `Trying to return resource not rented should return RESOURCE NOT RENT`(){
        val expected = ErrorDto("Rent does not exist", 3)

        val response = RestAssured.given()
                .port(8080)
                .baseUri("http://localhost/tks/api/library/item/EEEE-303/rent")
                .delete()

        response.then().statusCode(409)
        val body = response.body.`as`<ErrorDto>(ErrorDto::class.java)
        Assertions.assertEquals(expected, body)
    }
}