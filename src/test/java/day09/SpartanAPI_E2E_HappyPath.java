package day09;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import pojo.Spartan;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SpartanAPI_E2E_HappyPath {
    // we want the id that generated from post request accessible for all the tests
    static int newID;
    // We want exact order 1.Add, 2.Read, 3.Update, 4.Delete
    static Spartan sp1 = new Spartan("Leyla Ozdemir", "Female", 1231231231);

    @BeforeAll
    public static void init() {
        RestAssured.baseURI = "http://3.81.143.40";
        RestAssured.port = 8000;
        RestAssured.basePath = "/api";
    }

    @Order(1)
    @Test
    public void testAddData(){
        // create one data here using the POJO as body, do your assertion
        // gran the id so it can be used for all next 3 tests
        System.out.println("New ID is generated from the post request and saved " );
        Response response = given()
                .contentType(ContentType.JSON)
                .body(sp1).
                        when()
                .post("/spartans");
        newID = response.jsonPath().getInt("data.id");
        System.out.println(newID);
    }
    @Order(2)
    @Test
    public void testReadData(){
        // use the ID that have been generated from previous request
        // assert the response json according to expected data
        // expected data is the same data you used to create in previous request
        // you can make your post body from previous request at class level
        // so it can be accessible here
        // or you can also query the DB to get your expected data
        System.out.println(" The ID from Add Data Test is "  + newID);
        System.out.println("Using this ID for get Request ");
        Response response = given()
                .log().all()
                .pathParam("id",newID).
                        when()
                .get("/spartans/{id}");
        assertEquals(response.jsonPath().getInt("id"),newID);
    }
    @Order(3)
    @Test
    public void testUpdateData(){
        given()
                .contentType(ContentType.JSON)
                .formParam("name","Ahmet")
                .formParam("gender","Male")
                .formParam("phone","1234567890")
                .pathParam("id",newID).
                when()
                .put("/spartans/{id}");
        System.out.println(" The ID from Add Data Test is "  + newID);
        System.out.println("Using this ID for PUT Request ");
    }
    @Order(4)
    @Test
    public void testDeleteData(){
        given()
                .pathParam("id",newID).
                when()
                .delete("/spartans/{id}");
        System.out.println(" The ID from Add Data Test is "  + newID);
        System.out.println("Using this ID for DELETE Request ");
    }
}