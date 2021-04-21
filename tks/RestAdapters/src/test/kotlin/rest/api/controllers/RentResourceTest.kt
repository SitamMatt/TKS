package rest.api.controllers

import io.restassured.RestAssured
import org.junit.jupiter.api.Test

class RentResourceTest {
    @Test
    fun `Given valid email and accessing number rent should success`() {
        RestAssured.given()
                .port(8080)
                .baseUri("http://localhost/tks/api/library/item/EEE-254/rent")
                .post()
                .then()
                .statusCode(200)
    }
}