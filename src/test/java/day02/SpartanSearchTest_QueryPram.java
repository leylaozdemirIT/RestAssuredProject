package day02;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class SpartanSearchTest_QueryPram {

    @BeforeAll
    public static void setUp() {

        RestAssured.baseURI = "http://54.174.216.245:8000";
        RestAssured.basePath = "/api";

    }

    @DisplayName("Testing /spartans/search Endpoint")
    @Test
    public void testSearch() {

        given()
                .log().all()
                .queryParam("gender","Male")
                .queryParam("nameContains","e").
        when()
                .get("/spartans/search").
        then()
                .log().all()
                .statusCode(is(200))
                // assert
               .body("numberOfElements", is(63));
    }
}