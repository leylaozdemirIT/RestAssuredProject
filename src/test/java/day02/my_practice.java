package day02;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.* ;

public class my_practice {

    @Test
    public void test1(){


        Response response =
        given().  // add all your request specific information like header, query param, path var, body
                header("Accept", "application/json").
                auth().basic("admin","admin").
                pathParam("id",25).
        when().
                get("http://100.26.166.36:8000/api/spartans/{id}").
                prettyPeek();

        assertThat(response.statusCode(), is(200));
        assertThat(response.contentType(), is(equalTo("application/json;charset=UTF-8")));
        assertThat(response.body().path("id").toString(), is("25"));
        assertThat(response.body().path("name").toString(), is("Valentin"));
        assertThat(response.body().path("gender"),is("Male"));
        assertThat(response.body().path("phone"),is(1536037088));


    }



}
