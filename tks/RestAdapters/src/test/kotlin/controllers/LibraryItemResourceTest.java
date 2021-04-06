package controllers;

import dto.LibraryItemDto;
import io.restassured.http.ContentType;
import org.hamcrest.core.Is;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.*;

class LibraryItemResourceTest {

    @Test
    public void GivenValidLibraryItem_CreateShouldSuccess(){
        var model = new LibraryItemDto(null, "Diuna", "Frank Herbert", null, "Book");
        given()
                .port(8080)
                .baseUri("http://localhost/tks/api/library/item")
                .contentType(ContentType.JSON)
                .body(model)
                .post()
                .then()
                .statusCode(201)
                .header("Location", StringContains.containsString("/library/item/example"));
    }

}