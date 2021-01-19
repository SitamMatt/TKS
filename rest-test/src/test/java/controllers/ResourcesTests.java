package controllers;

import helpers.Helpers;
import io.restassured.RestAssured;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class ResourcesTests {


    @Before
    public void setup(){
        RestAssured.baseURI = "https://localhost:8181/pas-rest-1.0-SNAPSHOT";
    }

    @Test
    public void successfulGetAvailableResources() throws JSONException {
        var token = Helpers.extractToken(Helpers.auth("user", "user0"));
        var result = given()
                .relaxedHTTPSValidation()
                .headers("Authorization", "Bearer " + token)
                .when()
                .get("api/resources/available");
        result.then()
                .contentType("application/json")
                .statusCode(200);
        var array = new JSONArray(result.body().asString());
        var obj = new JSONObject(array.get(0).toString());

        Assert.assertEquals(obj.get("author"), "Andrew Sapkowsky");
        Assert.assertEquals(obj.get("title"), "Wieśmin");
    }

    @Test
    public void successfulGetMyCurrentRents() throws JSONException {
        var token = Helpers.extractToken(Helpers.auth("user", "user0"));
        var result = given()
                .relaxedHTTPSValidation()
                .headers("Authorization", "Bearer " + token)
                .when()
                .get("api/resources/my/active");
        result.then()
                .contentType("application/json")
                .statusCode(200);
        var array = new JSONArray(result.body().asString());
        var obj = new JSONObject(array.get(0).toString());

        Assert.assertEquals(array.length(), 1);
        Assert.assertEquals(obj.get("author"), "J.K Rollin");
        Assert.assertEquals(obj.get("title"), "Hurry Potter");
    }

    @Test
    public void successfulRentAllocationByClient() throws JSONException {
        var token = Helpers.extractToken(Helpers.auth("user", "user0"));
        var preCondition = given()
                .relaxedHTTPSValidation()
                .headers("Authorization", "Bearer " + token)
                .when()
                .get("api/resources/my/active");
        preCondition.then()
                .contentType("application/json")
                .statusCode(200);
        var preArray = new JSONArray(preCondition.body().asString());
        var preLength = preArray.length();

        var preResult = given()
                .relaxedHTTPSValidation()
                .headers("Authorization", "Bearer " + token)
                .when()
                .get("api/resources/available");
        preResult.then()
                .contentType("application/json")
                .statusCode(200);
        var array = new JSONArray(preResult.body().asString());
        var obj = new JSONObject(array.get(0).toString());

        var guid = obj.get("guid");

        var result = given().relaxedHTTPSValidation()
                .headers("Authorization", "Bearer " + token)
                .when()
                .post("https://localhost:8181/pas-rest-1.0-SNAPSHOT/api/resources/" + guid + "/rent");
        result.then()
                .statusCode(200);

        var postCondition = given()
                .relaxedHTTPSValidation()
                .headers("Authorization", "Bearer " + token)
                .when()
                .get("api/resources/my/active");
        postCondition.then()
                .contentType("application/json")
                .statusCode(200);
        var postArray = new JSONArray(postCondition.body().asString());
        Assert.assertEquals(postArray.length(), preLength + 1);
    }

    @Test
    public void wrongTryToRentLockedResource() throws JSONException {
        var token = Helpers.extractToken(Helpers.auth("testo", "test0"));
        var result = given()
                .relaxedHTTPSValidation()
                .headers("Authorization", "Bearer " + token)
                .when()
                .post("api/resources/1514f5ae-f54d-4b4f-ac97-97f32fe18cb0/rent");
        result.then()
                .statusCode(405);
    }

    @Test
    public void successfulReturnResource() throws JSONException {
        var token = Helpers.extractToken(Helpers.auth("user", "user0"));
        var preCondition = given()
                .relaxedHTTPSValidation()
                .headers("Authorization", "Bearer " + token)
                .when()
                .get("api/resources/my/active");
        preCondition.then()
                .contentType("application/json")
                .statusCode(200);
        var preArray = new JSONArray(preCondition.body().asString());
        var preLength = preArray.length();

        var result = given()
                .relaxedHTTPSValidation()
                .headers("Authorization", "Bearer " + token)
                .when()
                .post("api/resources/1514f5ae-f54d-4b4f-ac97-97f32fe18cb0/return");
        result.then()
                .statusCode(200);

        var postCondition = given()
                .relaxedHTTPSValidation()
                .headers("Authorization", "Bearer " + token)
                .when()
                .get("api/resources/my/active");
        postCondition.then()
                .contentType("application/json")
                .statusCode(200);
        var postArray = new JSONArray(postCondition.body().asString());
        Assert.assertEquals(postArray.length(), preLength - 1);
    }
}
