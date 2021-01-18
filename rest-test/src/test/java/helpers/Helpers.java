package helpers;

import org.json.JSONException;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class Helpers {

    public static String auth(String login, String password) throws JSONException {
        JSONObject jsonObj = new JSONObject()
                .put("login",login)
                .put("password",password);
        var res = given()
                .relaxedHTTPSValidation()
                .contentType("application/json")
                .body(jsonObj.toString())
                .when()
                .post("/api/auth/login");
        res.then()
                .statusCode(202);
        return res.body().asString();
    }

    public static String extractToken(String body) throws JSONException {
        JSONObject jsonObj = new JSONObject(body);
        return (String) jsonObj.get("token");
    }
}
