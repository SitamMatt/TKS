package controllers;

import helpers.Helpers;
import io.restassured.RestAssured;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class AuthControllerTests {
    String tokenSchema = "{\n" +
            "    \"$schema\": \"https://json-schema.org/draft/2019-09/schema\",\n" +
            "    \"title\": \"Token response\",\n" +
            "    \"type\": \"object\",\n" +
            "    \"properties\": {\n" +
            "        \"token\": {\n" +
            "            \"type\": \"string\"\n" +
            "        },\n" +
            "        \"expires\": {\n" +
            "            \"type\": \"string\",\n" +
            "            \"format\": \"date-time\"\n" +
            "        }\n" +
            "    },\n" +
            "    \"required\": [\n" +
            "        \"token\", \"expires\"\n" +
            "    ]\n" +
            "}";

    @Before
    public void setup() {
        RestAssured.baseURI = "https://localhost:8181/pas-rest-1.0-SNAPSHOT";
    }

    @Test
    public void nonActiveUserShouldGetRejectedWhenAcquiringToken() throws JSONException {
        JSONObject jsonObj = new JSONObject()
                .put("login","worker1")
                .put("password","worker1");
        var res = given()
                .relaxedHTTPSValidation()
                .contentType("application/json")
                .body(jsonObj.toString())
                .when()
                .post("/api/auth/login");
        res.then()
                .statusCode(400);
    }

    @Test
    public void authShouldSuccessForActiveUser() throws JSONException {
        JSONObject jsonObj = new JSONObject()
                .put("login","admin")
                .put("password","admin0");
        var res = given()
                .relaxedHTTPSValidation()
                .contentType("application/json")
                .body(jsonObj.toString())
                .when()
                .post("/api/auth/login");
        res.then()
                .statusCode(202)
                .contentType("application/json")
                .body(matchesJsonSchema(tokenSchema));
    }

    @Test
    public void tokenRefreshTest() throws JSONException {
        String token = Helpers.extractToken(Helpers.auth("user", "user0"));

        var res = given().relaxedHTTPSValidation()
                .headers("Authorization", "Bearer " + token)
                .when().get("/api/auth/refresh");
        res.then()
                .statusCode(202)
                .contentType("application/json")
                .body(matchesJsonSchema(tokenSchema));

        JSONObject obj = new JSONObject(res.body().asString());
        String refreshedToken = obj.getString("token");

        var res1 = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + refreshedToken)
                .when().get("/api/users/me");
        res1.then()
                .contentType("application/json")
                .statusCode(200);
    }

    @Test
    public void wrongCredentialsTest() throws JSONException {
        JSONObject jsonObj = new JSONObject()
                .put("login","worker1")
                .put("password","wrongpassword");
        var res = given()
                .relaxedHTTPSValidation()
                .contentType("application/json")
                .body(jsonObj.toString())
                .when()
                .post("/api/auth/login");
        res.then()
                .statusCode(400);
    }

    @Test
    public void inactiveUserRefreshTest() throws JSONException {
        String token = Helpers.extractToken(Helpers.auth("root", "root"));

        var res = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + token)
                .when().get("/api/users/me");
        res.then()
                .contentType("application/json")
                .statusCode(200);
        var json = new JSONObject(res.body().asString());

        String etag = res.getHeaders().get("Etag").getValue();
        etag = etag.substring(1, etag.length()-1);

        json.put("active", "false");
        var rs = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + token, "If-Match", etag)
                .body(json.toString()).when().put("/api/users/3fbdbdb6-7a44-4b9e-be8d-dd120a271b5b");
        rs.then()
                .statusCode(200);

        var res1 = given().relaxedHTTPSValidation()
                .headers("Authorization", "Bearer " + token)
                .when().get("/api/auth/refresh");
        res1.then()
                .statusCode(401);

    }
}
