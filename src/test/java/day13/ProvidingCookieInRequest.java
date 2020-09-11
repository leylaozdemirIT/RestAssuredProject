package day13;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class ProvidingCookieInRequest {

    // the code for providing cooke
    // for GET https://postman-echo.com/cookies request

    @Test
    public void testCookie(){

        given()
                .cookie("B18","Awesome")
                .cookie("Motto","Hold your horse").
        when()
                .get("https://postman-echo.com/cookies").
        then()
                .cookie("B18","a");

    }
}
