package controllers;

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
}
