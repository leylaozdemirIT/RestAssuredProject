package day03;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;

public class ExtractingDataOutOfResponseBody {

    @DisplayName("Getting Movie info")
    @Test
    public void test1() {

        // provide your baseURI in the test
        // add your query parameters
        // apikey, t
        // Save the response into response object

        Response response =
        given()
                .log().all()
                .baseUri("http://www.omdbapi.com")
                .queryParam("apikey", "26aa5b74")
                .queryParam("t", "Boss Baby").
        when()
                .get();
        
        response.prettyPrint();
        System.out.println( response.statusCode() );
        
        // if you want to get single data out for example just title, just year
        // use path method of Response object and provide the jsonPath
        
        String title = response.path("Title");
        System.out.println("title = " + title);

        // using same example -- print out -- Year, Director, Actors
        String year = response.path("Year");
        String director = response.path("Director");
        String actors = response.path("actors");

        System.out.println("year = " + year);
        System.out.println("director = " + director);
        System.out.println("actors = " + actors);

        // getting the first Rating Source
        // the jsonPath for first Rating Source is Ratings[0].Source
        String resource = response.path("Ratings[0].Source");
        System.out.println("firstRatingSource = " + resource);

        
    }
}