package controllers;

import helpers.Helpers;
import io.restassured.RestAssured;
import model.UserData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class UserControllerTests {

    public static final String UserSchema = "{\n" +
            "    \"$schema\": \"https://json-schema.org/draft/2019-09/schema\",\n" +
            "    \"title\": \"User\",\n" +
            "    \"type\": \"object\",\n" +
            "    \"properties\": {\n" +
            "        \"login\": {\n" +
            "            \"type\": \"string\"\n" +
            "        },\n" +
            "        \"role\": {\n" +
            "            \"type\": \"string\",\n" +
            "            \"enum\": [\n" +
            "                \"CLIENT\",\n" +
            "                \"ADMIN\",\n" +
            "                \"WORKER\"\n" +
            "            ]\n" +
            "        },\n" +
            "        \"active\": {\n" +
            "            \"type\": \"boolean\"\n" +
            "        },\n" +
            "        \"firstname\": {\n" +
            "            \"type\": \"string\"\n" +
            "        },\n" +
            "        \"lastname\": {\n" +
            "            \"type\": \"string\"\n" +
            "        },\n" +
            "        \"guid\": {\n" +
            "            \"type\": \"string\",\n" +
            "            \"format\": \"uuid\"\n" +
            "        }\n" +
            "    },\n" +
            "    \"required\": [\n" +
            "        \"guid\",\n" +
            "        \"login\",\n" +
            "        \"role\",\n" +
            "        \"active\"\n" +
            "    ]\n" +
            "}";

    @Before
    public void setup() {
        RestAssured.baseURI = "https://localhost:8181/pas-rest-1.0-SNAPSHOT";
    }

    @Test
    public void getIdTest() throws JSONException {
        String token = Helpers.extractToken(Helpers.auth("admin", "admin0"));
        String userId = "f9b3a442-b637-4e23-ad71-910ee816453e";

        var res = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + token)
                .when().get("/api/users/" + userId);
        res.then()
                .contentType("application/json")
                .body(matchesJsonSchema(UserSchema))
                .statusCode(200);
        var json = new JSONObject(res.body().asString());

        Assert.assertTrue(res.getHeaders().get("Etag").getValue().length() > 0);
        Assert.assertEquals(json.get("active"), true);
        Assert.assertEquals(json.get("role"), "CLIENT");
        Assert.assertEquals(json.get("firstname"), "Łukasz");
        Assert.assertEquals(json.get("lastname"), "Stanisławowski");
        Assert.assertEquals(json.get("login"), "testo");
        Assert.assertEquals(json.get("password"), "test0");
    }

    @Test
    public void getIdNotFoundTest() throws JSONException {
        String token = Helpers.extractToken(Helpers.auth("admin", "admin0"));
        String userId = "f9b3a442-b637-4323-ab71-910ee816453e";

        var res = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + token)
                .when().get("/api/users/" + userId);
        res.then()
                .contentType("text/html")
                .statusCode(404);
    }

    @Test
    public void getIdIncorrectTest() throws JSONException {
        String token = Helpers.extractToken(Helpers.auth("admin", "admin0"));
        String userId = "f9b3a442-b637-LOSOWEZNAKI-910ee816453e";

        var res = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + token)
                .when().get("/api/users/" + userId);
        res.then()
                .contentType("text/html")
                .statusCode(400);
    }

    @Test
    public void getIdUnauthorizedTest() {
        String userId = "f9b3a442-b637-4e23-ad71-910ee816453e";

        var res = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .when().get("/api/users/" + userId);
        res.then()
                .contentType("text/html")
                .statusCode(401);
    }

    @Test
    public void getIdForbiddenTest() throws JSONException {
        String token = Helpers.extractToken(Helpers.auth("user", "user0"));
        String userId = "f9b3a442-b637-4e23-ad71-910ee816453e";

        var res = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + token)
                .when().get("/api/users/" + userId);
        res.then()
                .statusCode(403);
    }

    @Test
    public void getAllTest() throws JSONException {
        String token = Helpers.extractToken(Helpers.auth("admin", "admin0"));

        var res = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + token)
                .when().get("/api/users");
        res.then()
                .contentType("application/json")
                .statusCode(200);

        var jsonArray = new JSONArray(res.body().asString());
        var obj = new JSONObject(jsonArray.get(0).toString());

        Assert.assertEquals(obj.get("firstname"), "Łukasz");
        Assert.assertTrue(jsonArray.length() >= 7);
    }

    @Test
    public void getByNameTest() throws JSONException {
        String token = Helpers.extractToken(Helpers.auth("admin", "admin0"));
        String name = "Janusz";

        var res = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + token)
                .queryParam("search", name)
                .when().get("/api/users");
        res.then()
                .contentType("application/json")
                .statusCode(200);

        var jsonArray = new JSONArray(res.body().asString());
        var obj = new JSONObject(jsonArray.get(0).toString());

        Assert.assertEquals(obj.get("firstname"), name);
        Assert.assertEquals(jsonArray.length(), 1);
    }

    @Test
    public void getByGroupTest() throws JSONException {
        String token = Helpers.extractToken(Helpers.auth("admin", "admin0"));
        String group = "WORKER";

        var res = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + token)
                .queryParam("type", group)
                .when().get("/api/users");
        res.then()
                .contentType("application/json")
                .statusCode(200);

        var jsonArray = new JSONArray(res.body().asString());
        var obj = new JSONObject(jsonArray.get(0).toString());

        Assert.assertEquals(obj.get("role"), group);
        Assert.assertTrue(jsonArray.length() >= 2);
    }

    @Test
    public void getEmptyTest() throws JSONException {
        String token = Helpers.extractToken(Helpers.auth("admin", "admin0"));
        String name = "Andrzej";

        var res = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + token)
                .queryParam("search", name)
                .when().get("/api/users");
        res.then()
                .statusCode(404);

    }

    @Test
    public void getUnauthorizedTest() {
        var res = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .when().get("/api/users");
        res.then()
                .statusCode(401);

    }

    @Test
    public void getForbiddenTest() throws JSONException {
        String token = Helpers.extractToken(Helpers.auth("user", "user0"));
        String name = "Janusz";

        var res = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + token)
                .queryParam("search", name)
                .when().get("/api/users");
        res.then()
                .statusCode(403);
    }

    @Test
    public void getMeTest() throws JSONException {
        String token = Helpers.extractToken(Helpers.auth("admin", "admin0"));

        var res = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + token)
                .when().get("/api/users/me");
        res.then()
                .contentType("application/json")
                .statusCode(200);

        var obj = new JSONObject(res.body().asString());

        Assert.assertTrue(res.getHeaders().get("Etag").getValue().length() > 0);
        Assert.assertEquals(obj.get("firstname"), "Janusz");
        Assert.assertEquals(obj.get("role"), "ADMIN");
    }

    @Test
    public void getMeUnauthorizedTest() {
        var res = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .when().get("/api/users/me");
        res.then()
                .statusCode(401);
    }

    @Test
    public void addTest() throws JSONException {
        String token = Helpers.extractToken(Helpers.auth("admin", "admin0"));
        UserData newUser = new UserData(
                true, "Bartek", "Bartkowski", UUID.randomUUID().toString(),
                "login", "WORKER", "hasło");

        JSONObject newUserJO = new JSONObject(newUser);

        var res = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + token)
                .body(newUserJO.toString())
                .when().post("/api/users");
        res.then()
                .statusCode(201);
    }

    @Test
    public void addTakenLoginTest() throws JSONException {
        String token = Helpers.extractToken(Helpers.auth("admin", "admin0"));
        UserData newUser = new UserData(
                true, "Bartek", "Bartkowski", UUID.randomUUID().toString(),
                "admin", "WORKER", "hasło");

        JSONObject newUserJO = new JSONObject(newUser);

        var res = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + token)
                .body(newUserJO.toString())
                .when().post("/api/users");
        res.then()
                .statusCode(409);
    }

    @Test
    public void addIncorrectTest() throws JSONException {
        String token = Helpers.extractToken(Helpers.auth("admin", "admin0"));
        UserData newUser = new UserData(
                true, "Bartek", "Bartkowski", UUID.randomUUID().toString(),
                "", "WORKER", "hasło");

        JSONObject newUserJO = new JSONObject(newUser);

        var res = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + token)
                .body(newUserJO.toString())
                .when().post("/api/users");
        res.then()
                .statusCode(400);
    }

    @Test
    public void addUnauthorized() throws JSONException {
        UserData newUser = new UserData(
                true, "Bartek", "Bartkowski", UUID.randomUUID().toString(),
                "nowylogin", "WORKER", "hasło");

        JSONObject newUserJO = new JSONObject(newUser);

        var res = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .body(newUserJO.toString())
                .when().post("/api/users");
        res.then()
                .statusCode(401);
    }

    @Test
    public void addForbidden() throws JSONException {
        UserData newUser = new UserData(
                true, "Bartek", "Bartkowski", UUID.randomUUID().toString(),
                "nowylogin", "WORKER", "hasło");

        JSONObject newUserJO = new JSONObject(newUser);
        String token = Helpers.extractToken(Helpers.auth("worker", "worker0"));
        var res1 = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .body(newUserJO.toString())
                .headers("Authorization", "Bearer " + token)
                .when().post("/api/users");
        res1.then()
                .statusCode(403);

    }


}
