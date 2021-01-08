import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class Tests {

    @Test
    public void test(){
        given().get("http://localhost:8080/pas-rest-1.0-SNAPSHOT/api/example").then().statusCode(200);
    }
}
