package day02;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class OpenMovieDB_Test {

    @BeforeAll
    public static void init() {

        RestAssured.baseURI = "http://www.omdbapi.com";
    }
    @DisplayName("Get favorite movie")
    @Test
    public void testMovieEndpoint() {

        given()
                .log().all()
                .queryParam("apikey","be8cd0d1")
                .queryParam("t","Sliding Doors")
                .queryParam("plot","full").
        when()
                .get(). // what if my URL is already complete, DO NOTHING
        then()
                .log().all()
                .statusCode( is(200))
                .body("Title", is("Sliding Doors"))
               .body("Year",is("1998"))
                .body("Ratings[0].Value", is("6.7/10"))
                .body("Ratings[-1].Value", is("59/100"));
    }
    }

