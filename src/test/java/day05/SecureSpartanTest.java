package day05;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.Spartan;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class SecureSpartanTest {

    // These instances are created from the image : Cybertek_Latest_BasicAuth
    // so it requires username password from Cybertek_Latest_NoAuth
    // it has the Spartan App version that does not require username and password

    // add @BeforeAll and use the spartanApp ID with basic auth
    @BeforeAll
    public static void init(){

        //54.160.106.84 - Akbar IPs
        //100.24.235.129
        //23.23.75.140

        RestAssured.baseURI = "http://100.24.235.129" ;
        RestAssured.port = 8000 ;
        RestAssured.basePath = "/api" ;

    }
    @DisplayName("Test GET/spartan/{id} Endpoint with No Credentials")
    @Test
    public void testGetSingleSpartanSecured(){
        given()
                .log().all()
                .pathParam("id",174).
        when()
                .get("/spartans/{id}").
        then()
                .log().all()
                .statusCode(401)
        ;

    }
    @DisplayName("Test GET/spartan/{id} Endpoint with Credentials")
    @Test
    public void testGettingSpartanWithCredentials(){

        int newId = createRandomSpartan();

        given()
                .log().all()
                .auth().basic("admin","admin")
                .pathParam("id",newId).
        when()
                .get("/spartans/{id}").
        then()
                .log().all()
                .statusCode(200)
        ;

    }

    public static int createRandomSpartan(){
        // use faker to get random first name, gender and 10 digit number for phone
        Faker faker = new Faker();

        String name = faker.name().firstName();
        String gender = faker.demographic().sex();
        long phone = faker.number().numberBetween(1000000000l, 9999999999l);

        Spartan sp = new Spartan(name, gender, phone);

        Response response = given()
                                    .log().all()
                                    .auth().basic("admin","admin")
                                    .contentType(ContentType.JSON)
                                    .body(sp).
                            when()
                                    .post("/spartans")
                                    .prettyPeek();

        return response.jsonPath().getInt("data.id");
    }

}
