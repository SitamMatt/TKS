package rest.api.controllers;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;

class RentResourceTest {

    @Test
    public void GivenValidEmailAndAccessingNumber_Rent_ShouldSuccess(){
        given()
                .port(8080)
                .baseUri("http://localhost/tks/api/library/item/QRWV-954/rent")
                .post()
                .then()
                .statusCode(200);
//                .header("Location", StringContains.containsString("/user/mszewc@edu.pl"))
//                .assertThat()
//                .body("$", not(hasKey("password")))
//                .body("active", Is.is(true));
    }

}