package day09;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.Spartan;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class SpartanRoleBaseAccessControlNegativeTest_Reuse {


    @BeforeAll
    public static void init(){
        RestAssured.baseURI = "http://54.160.106.84";
        RestAssured.port = 8000;
        RestAssured.basePath = "/api";

    }

    @DisplayName("User should not be able to delete data")
    @Test
    public void testUserCanNotDeleteData(){

        // building reusable request specification
        // using RequestSpecBuilder class

        RequestSpecification requestSpec = given()
                .auth().basic("user", "user")
                .accept(ContentType.JSON) // we are getting 403 with json body so accept header is json
                .log().all();


        // Extracting ResponseSpecification for all assertions so we can reuse
        // We will be using a class called ResponseSpecBuilder
        ResponseSpecBuilder resSpecBuilder = new ResponseSpecBuilder() ;
        // Getting the reusable ResponseSpecification object using the builder methods chaining
        ResponseSpecification responseSpec = resSpecBuilder.expectStatusCode(403)
                                              .expectContentType(ContentType.JSON)
                                              .expectHeader("Date", notNullValue(String.class))
                                              .log(LogDetail.ALL)
                                              .build(); // build method will return ResponseSpecification

                            // expectHeader second argument expect a Matcher<String>
                            // but notNullValue() return a Matcher<Object> so it did not compile
                            // so we used the second version of notNullValue( The Matcher type inside <>)
                            // to specify what kind of matcher we wanted

        given()
                .spec(requestSpec).
        when()
                .delete("/spartans/{id}" , 10).
        then()
                .spec(responseSpec);

    }

    @DisplayName("User should not be able to update data")
    @Test
    public void testUserCanNotUpdateData(){

        Spartan spartanObj = new Spartan("some name", "Male", 8888888888L) ;

        RequestSpecification req = given()
                .auth().basic("user","user")
                .accept(ContentType.JSON)
                .log().all()
                .contentType(ContentType.JSON)
                .body(spartanObj);

        ResponseSpecBuilder resSpecBuilder = new ResponseSpecBuilder() ;

        ResponseSpecification responseSpecification= resSpecBuilder.expectStatusCode(403)
                                .expectContentType(ContentType.JSON)
                                .log(LogDetail.ALL)
                                .expectHeader("Date", notNullValue(String.class))
                                .build();
        given()
                .spec(req).
        when()
                .put("/spartans/{id}",10).
        then()
                .spec(responseSpecification);
    }

    @DisplayName("User should not be able to post data")
    @Test
    public void testUserCanNotPostData(){

        Spartan spartanObj = new Spartan("some name", "Male", 8888888888L) ;

        RequestSpecification requestSpecification = given()
                                                            .auth().basic("user","user")
                                                            .accept(ContentType.JSON)
                                                            .log().all()
                                                            .contentType(ContentType.JSON)
                                                            .body(spartanObj);
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();

        ResponseSpecification responseSpecification = responseSpecBuilder
                                                .expectStatusCode(403)
                                                .expectHeader("Date",notNullValue(String.class))
                                                .expectContentType(ContentType.JSON)
                                                .log(LogDetail.ALL).build();
        given()
                .spec(requestSpecification).
        when()
                .post("/spartans").
        then()
                .spec(responseSpecification);

    }




}