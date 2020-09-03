package day07;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class NewsAPI_Test {

    @DisplayName("News API get all News Author if the Source id is not null")
    @Test
    public void testNews(){

        // Via the Authorization HTTP header. Bearer
        Response response = given()
                .baseUri("http://newsapi.org") // you can specify baseURI directly here if you only have one request and have no intention to share
                .basePath("/v2")
                .header("Authorization", "Bearer ce46d84d9fdc4f0e85ab4bf4fde57032")
                .queryParam("country","us")
                .log().all().
        when()
                .get("/top-headlines");

        JsonPath jp = response.jsonPath();

        List<String> allAuthor = jp.getList("articles.author");
        System.out.println("allAuthor = " + allAuthor.size());

        allAuthor.forEach(eachAuthor -> System.out.println("eachAuthor = " + eachAuthor));

        List<String> allAuthorFiltered =
                            jp.getList("articles.findAll{ it.source.id != null }.author");

        for(String author : allAuthorFiltered){

            System.out.println("author = " + author);
        }
        System.out.println("allAuthorFiltered = " + allAuthorFiltered.size());
    }

}
