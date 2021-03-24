import helpers.Helpers;
import io.restassured.RestAssured;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class HTTPSTests {
    @Before
    public void setup() {
        RestAssured.baseURI = "https://localhost:8181/pas-rest-1.0-SNAPSHOT";
    }

    public static final String HttpUri = "http://localhost:8080/pas-rest-1.0-SNAPSHOT";

    @Test
    public void httpRequestsShouldNotBeAllowed() throws JSONException {
        String token = Helpers.extractToken(Helpers.auth("admin", "admin0"));
        try {
            var res = given()
                    .contentType("application/json")
                    .headers("Authorization", "Bearer " + token)
                    .when().get(HttpUri + "/api/users/me");
            res.then()
                    .contentType("application/json")
                    .statusCode(302);
            Assert.fail();
        } catch (Exception ignored) {

        }
    }
}
