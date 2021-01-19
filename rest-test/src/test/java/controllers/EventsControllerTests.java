package controllers;

import helpers.Helpers;
import io.restassured.RestAssured;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class EventsControllerTests {
    String eventSchema = "{\n" +
            "    \"$schema\": \"https://json-schema.org/draft/2019-09/schema\",\n" +
            "    \"title\": \"Event\",\n" +
            "    \"type\": \"object\",\n" +
            "    \"properties\": {\n" +
            "        \"guid\": {\n" +
            "            \"type\": \"string\",\n" +
            "            \"format\": \"uuid\"\n" +
            "        },\n" +
            "        \"rentDate\": {\n" +
            "            \"type\": \"string\",\n" +
            "            \"format\": \"date-time\"\n" +
            "        },\n" +
            "        \"returnDate\": {\n" +
            "            \"type\": \"string\",\n" +
            "            \"format\": \"date-time\"\n" +
            "        },\n" +
            "        \"userId\": {\n" +
            "            \"type\": \"string\",\n" +
            "            \"format\": \"uuid\"\n" +
            "        },\n" +
            "        \"resourceId\": {\n" +
            "            \"type\": \"string\",\n" +
            "            \"format\": \"uuid\"\n" +
            "        }\n" +
            "    },\n" +
            "    \"required\": [\n" +
            "        \"guid\",\n" +
            "        \"userId\",\n" +
            "        \"resourceId\",\n" +
            "        \"rentDate\"\n" +
            "    ]\n" +
            "}";

    String eventListSchema = "{\n" +
            "    \"$schema\": \"https://json-schema.org/draft/2019-09/schema\",\n" +
            "    \"title\": \"EventList\",\n" +
            "    \"type\": \"array\",\n" +
            "    \"items\": [\n" +
            "        {\n" +
            "            \"type\": \"object\",\n" +
            "            \"properties\": {\n" +
            "                \"guid\": {\n" +
            "                    \"type\": \"string\",\n" +
            "                    \"format\": \"uuid\"\n" +
            "                },\n" +
            "                \"rentDate\": {\n" +
            "                    \"type\": \"string\",\n" +
            "                    \"format\": \"date-time\"\n" +
            "                },\n" +
            "                \"returnDate\": {\n" +
            "                    \"type\": \"string\",\n" +
            "                    \"format\": \"date-time\"\n" +
            "                },\n" +
            "                \"userId\": {\n" +
            "                    \"type\": \"string\",\n" +
            "                    \"format\": \"uuid\"\n" +
            "                },\n" +
            "                \"resourceId\": {\n" +
            "                    \"type\": \"string\",\n" +
            "                    \"format\": \"uuid\"\n" +
            "                }\n" +
            "            },\n" +
            "            \"required\": [\n" +
            "                \"guid\",\n" +
            "                \"userId\",\n" +
            "                \"resourceId\",\n" +
            "                \"rentDate\"\n" +
            "            ]\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    @Before
    public void setup(){
        RestAssured.baseURI = "https://localhost:8181/pas-rest-1.0-SNAPSHOT";
    }

    @Test
    public void successfulReadOperation() throws JSONException {
        var token = Helpers.extractToken(Helpers.auth("worker", "worker0"));
        var res = given()
                .relaxedHTTPSValidation()
                .headers("Authorization", "Bearer " + token)
                .when()
                .get("/api/events/fcb2c040-5b92-463d-bad8-818937d4f450");
        res.then()
                .statusCode(200)
                .body(matchesJsonSchema(eventSchema));
        var json2 = new JSONObject(res.body().asString());
        Assert.assertEquals("2011-12-31T23:00:00Z", json2.get("rentDate"));
        Assert.assertEquals("1514f5ae-f54d-4b4f-ac97-97f32fe18cb0", json2.get("resourceId"));
        Assert.assertEquals("48bb061d-0a01-4f60-bdfc-f6bac839b107", json2.get("userId"));
    }

    @Test
    public void unauthorizedRequest(){
        var res = given()
                .relaxedHTTPSValidation()
                .when()
                .get("/api/events/fcb2c040-5b92-463d-bad8-818937d4f450");
        res.then()
                .statusCode(401);
    }

    @Test
    public void forbiddenRolesShouldBeRejected() throws JSONException {
        // admin role
        var token = Helpers.extractToken(Helpers.auth("admin", "admin0"));
        given()
                .relaxedHTTPSValidation()
                .headers("Authorization", "Bearer " + token)
                .when()
                .get("/api/events/fcb2c040-5b92-463d-bad8-818937d4f450")
                .then()
                .statusCode(403);

        // client role
        token = Helpers.extractToken(Helpers.auth("testo", "test0"));
        given()
                .relaxedHTTPSValidation()
                .headers("Authorization", "Bearer " + token)
                .when()
                .get("/api/events/fcb2c040-5b92-463d-bad8-818937d4f450")
                .then()
                .statusCode(403);
    }

    @Test
    public void successfulReadAll() throws JSONException {
        var token = Helpers.extractToken(Helpers.auth("worker", "worker0"));
        var res = given()
                .relaxedHTTPSValidation()
                .headers("Authorization", "Bearer " + token)
                .when()
                .get("/api/events");
        res.then()
                .statusCode(200)
                .body(matchesJsonSchema(eventListSchema));
    }
}
