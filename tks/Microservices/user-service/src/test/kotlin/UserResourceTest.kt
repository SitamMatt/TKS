import common.error.ErrorDto
import edu.p.lodz.pl.service.users.dto.UserDto
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.hamcrest.Matchers
import org.hamcrest.core.Is
import org.hamcrest.core.StringContains
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class UserResourceTest {

    @Test
    fun `Given valid user email get should return corresponding user`() {
        val expected = UserDto("mszewc@edu.pl", null, true, "ADMIN")

        val response = RestAssured.given()
            .port(8080)
            .baseUri("http://localhost/user-service/app/user")
            .contentType(ContentType.JSON)
            .get("/mszewc@edu.pl")

        response.then().statusCode(200)
        val body = response.body.`as`<UserDto>(UserDto::class.java)
        Assertions.assertEquals(expected, body)
    }

    @Test
    fun `Given nonexistent user email, get should return NOT FOUND`(){
        val expected = ErrorDto("User not found", 1)

        val response = RestAssured.given()
            .port(8080)
            .baseUri("http://localhost/user-service/app/user")
            .get("/mim@edu.pl")

        response.then().statusCode(404)
        val body = response.body.`as`<ErrorDto>(ErrorDto::class.java)
        Assertions.assertEquals(expected, body)
    }

    @Test
    fun `Given invalid user email, get should return BAD REQUEST`(){
        val expected = ErrorDto("The given identifier format is invalid", 16)

        val response = RestAssured.given()
            .port(8080)
            .baseUri("http://localhost/user-service/app/user")
            .get("/xxx")

        response.then().statusCode(400)
        val body = response.body.`as`<ErrorDto>(ErrorDto::class.java)
        Assertions.assertEquals(expected, body)
    }

    @Test
    fun `Given valid user, post should create new user resource`() {
        val model = UserDto("matzab@edu.pl", "password", true, "CLIENT")
        val expected = UserDto("matzab@edu.pl", null, true, "CLIENT")

        RestAssured.given()
            .port(8080)
            .baseUri("http://localhost/user-service/app/user")
            .contentType(ContentType.JSON)
            .body(model)
            .post()
            .then()
            .statusCode(201)
            .header("Location", StringContains.containsString("/user/matzab@edu.pl"))
            .assertThat()
            .body("$", Matchers.not(Matchers.hasKey("password")))
            .body("active", Is.`is`(true))

        val response = RestAssured.given()
            .port(8080)
            .baseUri("http://localhost/user-service/app/user")
            .contentType(ContentType.JSON)
            .get("/matzab@edu.pl")

        response.then().statusCode(200)
        val body = response.body.`as`<UserDto>(UserDto::class.java)
        Assertions.assertEquals(expected, body)
    }

    @Test
    fun `Given user with duplicated email, post should return CONFLICT`() {
        val model1 = UserDto("mati@edu.pl", "password", true, "CLIENT")
        val model2 = UserDto("mati@edu.pl", "password", true, "CLIENT")
        val expected = ErrorDto("Email is taken", 13)

        RestAssured.given()
            .port(8080)
            .baseUri("http://localhost/user-service/app/user")
            .contentType(ContentType.JSON)
            .body(model1)
            .post()
            .then()
            .statusCode(201)
            .header("Location", StringContains.containsString("/user/mati@edu.pl"))
            .assertThat()
            .body("$", Matchers.not(Matchers.hasKey("password")))
            .body("active", Is.`is`(true))

        val response = RestAssured.given()
            .port(8080)
            .baseUri("http://localhost/user-service/app/user")
            .contentType(ContentType.JSON)
            .body(model2)
            .post()

        response.then().statusCode(409)
        val body = response.body.`as`<ErrorDto>(ErrorDto::class.java)
        Assertions.assertEquals(expected, body)
    }


}