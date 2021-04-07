package rest.api.controllers;

import rest.api.dto.UserDto;
import io.restassured.http.ContentType;
import org.hamcrest.core.Is;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;

class UserResourceTest {

    @Test
    public void test(){
        var model = new UserDto("mszewc@edu.pl", "####", true, "ADMIN");

        given()
                .port(8080)
                .baseUri("http://localhost/tks/api/user")
                .contentType(ContentType.JSON)
                .body(model)
                .post()
                .then()
                .statusCode(201)
                .header("Location", StringContains.containsString("/user/mszewc@edu.pl"))
                .assertThat()
                .body("$", not(hasKey("password")))
                .body("active", Is.is(true));
    }

    @Test
    public void GivenValidUserResource_PostShouldSuccessful(){
        var userdto = new UserDto("mszewc@edu.pl", "####", true, "ADMIN");
        given()
                .port(8080)
                .baseUri("http://localhost/tks/api/user")
                .contentType(ContentType.JSON)
                .body(userdto)
                .post()
                .then()
                .statusCode(201)
                .header("Location", StringContains.containsString("/user/mszewc@edu.pl"))
                .assertThat()
                .body("$", not(hasKey("password")))
                .body("active", Is.is(true));
    }

}