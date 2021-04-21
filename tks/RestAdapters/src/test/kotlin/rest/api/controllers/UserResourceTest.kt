package rest.api.controllers

import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.hamcrest.Matchers
import org.hamcrest.core.Is
import org.hamcrest.core.StringContains
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import rest.api.dto.UserDto

class UserResourceTest {

    @Test
    fun `Given valid user email get should return corresponding user`() {
        val expected = UserDto("mszewc@edu.pl", null, true, "ADMIN")

        val response = RestAssured.given()
            .port(8080)
            .baseUri("http://localhost/tks/api/user")
            .contentType(ContentType.JSON)
            .get("/mszewc@edu.pl")

        response.then().statusCode(200)
        val body = response.body.`as`<UserDto>(UserDto::class.java)

        Assertions.assertEquals(expected, body)
    }

    @Test
    fun `Given valid user, post should create new user resource`() {
        val model = UserDto("mszewc@edu.pl", "####", true, "ADMIN")
        RestAssured.given()
            .port(8080)
            .baseUri("http://localhost/tks/api/user")
            .contentType(ContentType.JSON)
            .body(model)
            .post()
            .then()
            .statusCode(201)
            .header("Location", StringContains.containsString("/user/mszewc@edu.pl"))
            .assertThat()
            .body("$", Matchers.not(Matchers.hasKey("password")))
            .body("active", Is.`is`(true))
    }
}