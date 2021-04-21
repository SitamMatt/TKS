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
                .baseUri("http://localhost/tks/api/library/item/EEEE-154/rent/by/mszewc@edu.pl")
                .post()
                .then()
                .statusCode(201)
    }

    @Test
    fun `Trying to rent resource already rented should return RESOURCE ALREADY RENT`(){
        val expected = ErrorDto("Resource already rented", 11)

        val response = RestAssured.given()
                .port(8080)
                .baseUri("http://localhost/tks/api/library/item/EEEE-254/rent/by/mszewc@edu.pl")
                .post()

        response.then().statusCode(409)
        val body = response.body.`as`<ErrorDto>(ErrorDto::class.java)
        Assertions.assertEquals(expected, body)
    }

    @Test
    fun `Given not valid email rent should return NOT FOUND`(){
        val expected = ErrorDto("User not found", 1)
        val response = RestAssured.given()
                .port(8080)
                .baseUri("http://localhost/tks/api/library/item/EEEE-214/rent/by/michal@edu.pl")
                .post()

        response.then().statusCode(404)
        val body = response.body.`as`<ErrorDto>(ErrorDto::class.java)
        Assertions.assertEquals(expected, body)
    }

    @Test
    fun `Given not valid accession number rent should return NOT FOUND`(){
        val expected = ErrorDto("Resource not found", 2)

        val response = RestAssured.given()
                .port(8080)
                .baseUri("http://localhost/tks/api/library/item/EEEE-214/rent/by/mszewc@edu.pl")
                .post()

        response.then().statusCode(404)
        val body = response.body.`as`<ErrorDto>(ErrorDto::class.java)
        Assertions.assertEquals(expected, body)
    }

    @Test
    fun `Given not active user email rent should return INACTIVE USER`(){
        val expected = ErrorDto("User is inactive", 12)

        val response = RestAssured.given()
                .port(8080)
                .baseUri("http://localhost/tks/api/library/item/EEEE-254/rent/by/marcin@edu.pl")
                .post()

        response.then().statusCode(400)
        val body = response.body.`as`<ErrorDto>(ErrorDto::class.java)
        Assertions.assertEquals(expected, body)
    }

    @Test
    fun `Given valid email and accessing number rent delete should success`(){
        RestAssured.given()
                .port(8080)
                .baseUri("http://localhost/tks/api/library/item/EEEE-303/rent/by/mszewc@edu.pl")
                .post()
                .then()
                .statusCode(201)

        RestAssured.given()
                .port(8080)
                .baseUri("http://localhost/tks/api/library/item/EEEE-303/rent/by/mszewc@edu.pl")
                .delete()
                .then()
                .statusCode(200)
    }

    @Test
    fun `Given not valid email rent delete should return NOT FOUND`(){
        val expected = ErrorDto("User not found", 1)
        val response = RestAssured.given()
                .port(8080)
                .baseUri("http://localhost/tks/api/library/item/EEEE-214/rent/by/michal@edu.pl")
                .delete()

        response.then().statusCode(404)
        val body = response.body.`as`<ErrorDto>(ErrorDto::class.java)
        Assertions.assertEquals(expected, body)
    }

    @Test
    fun `Given wrong user email rent delete should return INVALID USER`(){
        val expected = ErrorDto("Rent does not exist", 3)
        val response = RestAssured.given()
                .port(8080)
                .baseUri("http://localhost/tks/api/library/item/EEEE-154/rent/by/marcin@edu.pl")
                .delete()

        response.then().statusCode(400)
        val body = response.body.`as`<ErrorDto>(ErrorDto::class.java)
        Assertions.assertEquals(expected, body)
    }

    @Test
    fun `Given not valid accession number rent delete should return NOT FOUND`(){

        val expected = ErrorDto("Resource not found", 2)

        val response = RestAssured.given()
                .port(8080)
                .baseUri("http://localhost/tks/api/library/item/EEEE-214/rent/by/mszewc@edu.pl")
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
                .baseUri("http://localhost/tks/api/library/item/EEEE-303/rent/by/mszewc@edu.pl")
                .delete()

        response.then().statusCode(400)
        val body = response.body.`as`<ErrorDto>(ErrorDto::class.java)
        Assertions.assertEquals(expected, body)
    }
}