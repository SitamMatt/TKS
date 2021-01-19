package controllers;

import helpers.Helpers;
import io.restassured.RestAssured;
import model.BookData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;


public class ResourcesManagementTests {
    String resourceSchema = "{\n" +
            "    \"$schema\": \"https://json-schema.org/draft/2019-09/schema\",\n" +
            "    \"title\": \"Resource\",\n" +
            "    \"type\": \"object\",\n" +
            "    \"properties\": {\n" +
            "        \"author\": {\n" +
            "            \"type\": \"string\"\n" +
            "        },\n" +
            "        \"publishingHouse\": {\n" +
            "            \"type\": \"string\"\n" +
            "        },\n" +
            "        \"pagesCount\": {\n" +
            "            \"type\": \"number\"\n" +
            "        },\n" +
            "        \"title\": {\n" +
            "            \"type\": \"string\"\n" +
            "        },\n" +
            "        \"type\": {\n" +
            "            \"type\": \"string\",\n" +
            "\t\t\t\"enum\": [\n" +
            "                \"Magazine\",\n" +
            "                \"Book\"\n" +
            "            ]\n" +
            "        },\n" +
            "        \"guid\": {\n" +
            "            \"type\": \"string\",\n" +
            "            \"format\": \"uuid\"\n" +
            "        }\n" +
            "    },\n" +
            "    \"required\": [\n" +
            "        \"guid\",\n" +
            "        \"author\",\n" +
            "        \"title\",\n" +
            "        \"type\"\n" +
            "    ]\n" +
            "}";

    @Before
    public void setup(){
        RestAssured.baseURI = "https://localhost:8181/pas-rest-1.0-SNAPSHOT";
    }

    @Test
    public void successfulGetAllTest() throws JSONException {
        var token = Helpers.extractToken(Helpers.auth("worker", "worker0"));
        var result = given()
                .relaxedHTTPSValidation()
                .headers("Authorization", "Bearer " + token)
                .when()
                .get("api/resources/management");
        result.then()
                .contentType("application/json")
                .statusCode(200);
        var array = new JSONArray(result.body().asString());
        var obj = new JSONObject(array.get(0).toString());

        Assert.assertEquals(obj.get("author"), "J.K Rollin");
        Assert.assertEquals(obj.get("title"), "Hurry Potter");
    }

    @Test
    public void successfulGetByIdTest() throws JSONException {
        var token = Helpers.extractToken(Helpers.auth("worker", "worker0"));
        var result = given()
                .relaxedHTTPSValidation()
                .headers("Authorization", "Bearer " + token)
                .when()
                .get("/api/resources/management/1514f5ae-f54d-4b4f-ac97-97f32fe18cb0");
        result.then()
                .statusCode(200)
                .body(matchesJsonSchema(resourceSchema));
    }

    //todo proper response code at pas-rest (201)
    @Test
    public void successfulAddResourceTest() throws JSONException {
        var token = Helpers.extractToken(Helpers.auth("worker", "worker0"));
        var resData = new BookData(
                "Viedymin",
                342,
                "MegaNova",
                "Book",
                "Andrew Sapkowsky"
        );
        var obj = new JSONObject(resData);

        var res = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + token)
                .body(obj.toString())
                .when().post("/api/resources/management");
        res.then()
                .statusCode(200);
    }

    @Test
    public void successfulDeleteTest() throws JSONException {
        var token = Helpers.extractToken(Helpers.auth("worker", "worker0"));
        var beforeContent = given()
                .relaxedHTTPSValidation()
                .headers("Authorization", "Bearer " + token)
                .when()
                .get("/api/resources/management/c8168b00-b3e9-42da-afb9-1be9c44ceb44");
        beforeContent.then()
                .statusCode(200);

        var deleteRes = given()
                .relaxedHTTPSValidation()
                .headers("Authorization", "Bearer " + token)
                .when()
                .delete("/api/resources/management/c8168b00-b3e9-42da-afb9-1be9c44ceb44");
        deleteRes.then()
                .statusCode(200);

        var afterContent = given()
                .relaxedHTTPSValidation()
                .headers("Authorization", "Bearer " + token)
                .when()
                .get("/api/resources/management/c8168b00-b3e9-42da-afb9-1be9c44ceb44");
        afterContent.then()
                .statusCode(404);
    }

    @Test
    public void notFoundTest() throws JSONException {
        var token = Helpers.extractToken(Helpers.auth("worker", "worker0"));
        var resId = "1514f5ae-f54d-4b4f-fc97-97f32fe18cb0";

        var result = given()
                .relaxedHTTPSValidation()
                .headers("Authorization", "Bearer " + token)
                .when()
                .get("/api/resources/management/" + resId);
        result.then()
                .statusCode(404);
    }

    @Test
    public void wrongSyntaxTest() throws JSONException {
        var token = Helpers.extractToken(Helpers.auth("worker", "worker0"));
        var resData = new BookData(
                "",
                -342,
                "MegaNova",
                "Book",
                "Andrew Sapkowsky"
        );
        var obj = new JSONObject(resData);

        var res = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + token)
                .body(obj.toString())
                .when().post("/api/resources/management");
        res.then()
                .statusCode(400);
    }

    @Test
    public void unauthorizedAccessTest() throws JSONException {
        var token = Helpers.extractToken(Helpers.auth("user", "user0"));
        var deleteRes = given()
                .relaxedHTTPSValidation()
                .headers("Authorization", "Bearer " + token)
                .when()
                .delete("/api/resources/management/d929c7e8-cd04-4296-be9e-d890e9fcf19e");
        deleteRes.then()
                .statusCode(403);
    }

    @Test
    public void tryToDeleteLockedResourceTest() throws JSONException {
        var token = Helpers.extractToken(Helpers.auth("worker", "worker0"));
        var deleteRes = given()
                .relaxedHTTPSValidation()
                .headers("Authorization", "Bearer " + token)
                .when()
                .delete("/api/resources/management/1514f5ae-f54d-4b4f-ac97-97f32fe18cb0");
        deleteRes.then()
                .statusCode(405);
    }

}
