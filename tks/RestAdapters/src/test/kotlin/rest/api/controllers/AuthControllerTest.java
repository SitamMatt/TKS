package rest.api.controllers;

import rest.api.dto.LoginDto;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;

public class AuthControllerTest {

    @Test
    public void GivenValidCredentials_Login_ShouldSuccess(){
        var model = new LoginDto("mszewc@edu.pl", "####");
        var response = given()
                .port(8080)
                .baseUri("http://localhost/tks/api/auth/login")
                .contentType(ContentType.JSON)
                .body(model)
                .post();
        var body = response.body().asString();

    }
}
