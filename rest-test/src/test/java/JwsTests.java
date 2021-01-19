import helpers.Helpers;
import io.restassured.RestAssured;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class JwsTests {

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

    public static final String ResourceSchema = "{\n" +
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
    public void setup() {
        RestAssured.baseURI = "https://localhost:8181/pas-rest-1.0-SNAPSHOT";
    }

    @Test
    public void jwsUserUpdateTest() throws JSONException {
        String token = Helpers.extractToken(Helpers.auth("admin", "admin0"));
        var res = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + token)
                .when().get("/api/users/me");
        res.then()
                .contentType("application/json")
                .body(matchesJsonSchema(UserSchema))
                .statusCode(200);
        var json = new JSONObject(res.body().asString());

        String etag = res.getHeaders().get("Etag").getValue();
        etag = etag.substring(1, etag.length()-1);

        json.put("firstname", "Mateusz");
        var rs = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + token, "If-Match", etag)
                .body(json.toString()).when().put("/api/users/3fbabdb6-7a44-4b9e-be8d-dd120a271b5b");
        rs.then()
                .statusCode(200);



        var ress = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + token)
                .when().get("/api/users/me");
        ress.then()
                .contentType("application/json")
                .body(matchesJsonSchema(UserSchema))
                .statusCode(200);
        var json2 = new JSONObject(ress.body().asString());
        Assert.assertEquals("Mateusz", json2.get("firstname"));
    }

    @Test
    public void jwsUserUpdateUnauthorizedTest() throws JSONException {
        String token = Helpers.extractToken(Helpers.auth("admin", "admin0"));
        var res = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + token)
                .when().get("/api/users/me");
        res.then()
                .contentType("application/json")
                .body(matchesJsonSchema(UserSchema))
                .statusCode(200);
        var json = new JSONObject(res.body().asString());

        String etag = res.getHeaders().get("Etag").getValue();
        etag = etag.substring(1, etag.length()-1);

        json.put("firstname", "Mateusz");
        var rs = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .headers("If-Match", etag)
                .body(json.toString()).when().put("/api/users/3fbabdb6-7a44-4b9e-be8d-dd120a271b5b");
        rs.then()
                .statusCode(401);
    }

    @Test
    public void jwsUserUpdateForbiddenTest() throws JSONException {
        String token = Helpers.extractToken(Helpers.auth("admin", "admin0"));
        var res = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + token)
                .when().get("/api/users/me");
        res.then()
                .contentType("application/json")
                .body(matchesJsonSchema(UserSchema))
                .statusCode(200);
        var json = new JSONObject(res.body().asString());

        String etag = res.getHeaders().get("Etag").getValue();
        etag = etag.substring(1, etag.length()-1);

        String userToken = Helpers.extractToken(Helpers.auth("user", "user0"));

        json.put("firstname", "Mateusz");
        var rs = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + userToken,"If-Match", etag)
                .body(json.toString()).when().put("/api/users/3fbabdb6-7a44-4b9e-be8d-dd120a271b5b");
        rs.then()
                .statusCode(403);
    }

    @Test
    public void jwsUserUpdateInvalidEtagTest() throws JSONException {
        String token = Helpers.extractToken(Helpers.auth("admin", "admin0"));
        var res = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + token)
                .when().get("/api/users/me");
        res.then()
                .contentType("application/json")
                .body(matchesJsonSchema(UserSchema))
                .statusCode(200);
        var json = new JSONObject(res.body().asString());

        String etag = "";

        json.put("firstname", "Mateusz");
        var rs = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + token, "If-Match", etag)
                .body(json.toString()).when().put("/api/users/3fbabdb6-7a44-4b9e-be8d-dd120a271b5b");
        rs.then()
                .statusCode(412);
    }

    @Test
    public void jwsUserUpdateNoIfMatchTest() throws JSONException {
        String token = Helpers.extractToken(Helpers.auth("admin", "admin0"));
        var res = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + token)
                .when().get("/api/users/me");
        res.then()
                .contentType("application/json")
                .body(matchesJsonSchema(UserSchema))
                .statusCode(200);
        var json = new JSONObject(res.body().asString());


        json.put("firstname", "Mateusz");
        var rs = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + token)
                .body(json.toString()).when().put("/api/users/3fbabdb6-7a44-4b9e-be8d-dd120a271b5b");
        rs.then()
                .statusCode(400);
    }

    @Test
    public void jwsResourceUpdateTest() throws JSONException {
        String token = Helpers.extractToken(Helpers.auth("worker", "worker0"));
        String resourceId = "6d094ca9-94d1-480f-96ca-fce609ac4c44";

        var res = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + token)
                .when().get("/api/resources/management/" + resourceId);
        res.then()
                .contentType("application/json")
                .body(matchesJsonSchema(ResourceSchema))
                .statusCode(200);
        var json = new JSONObject(res.body().asString());

        String etag = res.getHeaders().get("Etag").getValue();
        etag = etag.substring(1, etag.length()-1);

        json.put("title", "E-BUK");
        var rs = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + token, "If-Match", etag)
                .body(json.toString()).when().put("/api/resources/management/" + resourceId);
        rs.then()
                .statusCode(200);


        var ress = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + token)
                .when().get("/api/resources/management/" + resourceId);
        ress.then()
                .contentType("application/json")
                .body(matchesJsonSchema(ResourceSchema))
                .statusCode(200);
        var json2 = new JSONObject(ress.body().asString());
        Assert.assertEquals("E-BUK", json2.get("title"));
    }

    @Test
    public void jwsResourceUpdateUnauthorizedTest() throws JSONException {
        String token = Helpers.extractToken(Helpers.auth("worker", "worker0"));
        String resourceId = "6d094ca9-94d1-480f-96ca-fce609ac4c44";

        var res = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + token)
                .when().get("/api/resources/management/" + resourceId);
        res.then()
                .contentType("application/json")
                .body(matchesJsonSchema(ResourceSchema))
                .statusCode(200);
        var json = new JSONObject(res.body().asString());

        String etag = res.getHeaders().get("Etag").getValue();
        etag = etag.substring(1, etag.length()-1);

        json.put("title", "E-BUK");
        var rs = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .headers("If-Match", etag)
                .body(json.toString()).when().put("/api/resources/management/" + resourceId);
        rs.then()
                .statusCode(401);
    }

    @Test
    public void jwsResourceUpdateForbiddenTest() throws JSONException {
        String token = Helpers.extractToken(Helpers.auth("worker", "worker0"));
        String resourceId = "6d094ca9-94d1-480f-96ca-fce609ac4c44";

        var res = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + token)
                .when().get("/api/resources/management/" + resourceId);
        res.then()
                .contentType("application/json")
                .body(matchesJsonSchema(ResourceSchema))
                .statusCode(200);
        var json = new JSONObject(res.body().asString());

        String etag = res.getHeaders().get("Etag").getValue();
        etag = etag.substring(1, etag.length()-1);

        String userToken = Helpers.extractToken(Helpers.auth("user", "user0"));

        json.put("title", "E-BUK");
        var rs = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + userToken, "If-Match", etag)
                .body(json.toString()).when().put("/api/resources/management/" + resourceId);
        rs.then()
                .statusCode(403);
    }


    @Test
    public void jwsResourceUpdateInvalidEtagTest() throws JSONException {
        String token = Helpers.extractToken(Helpers.auth("worker", "worker0"));
        String resourceId = "6d094ca9-94d1-480f-96ca-fce609ac4c44";

        var res = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + token)
                .when().get("/api/resources/management/" + resourceId);
        res.then()
                .contentType("application/json")
                .body(matchesJsonSchema(ResourceSchema))
                .statusCode(200);
        var json = new JSONObject(res.body().asString());

        String etag = "";

        json.put("title", "E-BUK");
        var rs = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + token, "If-Match", etag)
                .body(json.toString()).when().put("/api/resources/management/" + resourceId);
        rs.then()
                .statusCode(412);
    }

    @Test
    public void jwsResourceUpdateNoIfMatchTest() throws JSONException {
        String token = Helpers.extractToken(Helpers.auth("worker", "worker0"));
        String resourceId = "6d094ca9-94d1-480f-96ca-fce609ac4c44";

        var res = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + token)
                .when().get("/api/resources/management/" + resourceId);
        res.then()
                .contentType("application/json")
                .body(matchesJsonSchema(ResourceSchema))
                .statusCode(200);
        var json = new JSONObject(res.body().asString());


        json.put("title", "E-BUK");
        var rs = given().relaxedHTTPSValidation()
                .contentType("application/json")
                .headers("Authorization", "Bearer " + token)
                .body(json.toString()).when().put("/api/resources/management/" + resourceId);
        rs.then()
                .statusCode(400);
    }

}
