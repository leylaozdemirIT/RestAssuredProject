package day12;

import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class OpenMovie_XMLTest {

    @Test
    public void testMovieXML(){
       // http://www.omdbapi.com/?t=Boss Baby&r=xml
        given()
                .log().all()
                .baseUri("http://www.omdbapi.com/")
                .formParam("apikey","26aa5b74")
                .queryParam("t","Boss Baby")
                .queryParam("r","xml").
        when()
                .get().
        then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.XML)
                // the result has only 2 elements, parent:root, child:movie
                // all the movie data is stored under Movie attributes
                // below xmlpath is specifying go to the /root/movie and find the attribute of the movie element
                // called title
                .body("root.movie.@title",is("The Boss Baby"))
                //also validate year, rated, released, runtime
                .body("root.movie.@year",is("2017"))
                .body("root.movie.@rated",is("PG"))
                .body("root.movie.@released",is("31 Mar 2017"))
                .body("root.movie.@runtime",is("97 min"));
    }

    @DisplayName("Getting may movies and extracting attributes to the list")
    @Test
    public void testGettingAttributeAsList(){

        Response response = given()
                .log().all()
                .baseUri("http://www.omdbapi.com/")
                .formParam("apikey","26aa5b74")
                .queryParam("r","xml")
                .queryParam("s","Superman").
        when()
                .get();

        XmlPath xp = response.xmlPath();

        // get the list of movie title
        // getting list of attribute belong to each elements in xml nodes/elements
        List<String> supermanMovieList = xp.getList("root.result.@title");
        System.out.println("supermanMovieList = " + supermanMovieList);
    }
}
