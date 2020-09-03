package day02;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

// you may also add display name at class name like you did at test level
@DisplayName("Testing Zip Code API")
public class ZipCodeTest {

    @BeforeAll
    public static void setUp(){

        RestAssured.baseURI = "http://api.zippopotam.us" ;
        RestAssured.basePath = "/us" ;

    }
    @DisplayName( "Zip to City Test")
    @Test
    public void testZipToCity(){

        given()
                .log().all()
                .pathParam("zipcode",20850).
        when()
                .get("/{zipcode}").
        then()
                .log().all()
                .statusCode( is(200) )
                .contentType(ContentType.JSON)
                .body("country", is("United States"))
                .body("places[0].longitude", is("-77.168"))
                .body("places[0].state",is("Maryland"))
                // FIX FOR THE SPACE IN THE KEY
                .body("\"post code\"",is("20850"))
                // get the place name
                .body("places[0].'place name'", is("Rockville"))
        ;

        // if a field/key you are trying to get has space
        // then add '' for example " ' ' "
    }

    @DisplayName( "City to Zip Test")
    @Test
    public void testCityToZip(){

        given()
                .log().all()
               .pathParam("state","MD")
                .pathParam("city","Rockville").
        when()
                .get("/{state}/{city}").
              //  .get("/{state}/{city}", "MD","Rockville"). - second way
        then()
                .log().all()
                .statusCode( is(200) )
                .contentType(ContentType.JSON)
                .body("'country abbreviation'", is("US"))
                .body("country", is("United States"))
                .body("places[0].longitude", is("-77.2076"))
                .body("state",is("Maryland"))
                .body("places[3].\"post code\"",is("20850"))
                .body("places[3].'place name'", is("Rockville"))
                // jsonPath hack for getting last item from jsonArray
                // -1 index indicate the last item, only works here not in postman
                .body("places[-1].latitude", is("39.144"))
        ;

    }
}
