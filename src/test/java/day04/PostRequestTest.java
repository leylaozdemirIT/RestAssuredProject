package day04;

import com.github.javafaker.Faker;
import java.io.*;
import java.util.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class PostRequestTest {

    @BeforeAll
    public static void init(){

        RestAssured.baseURI = "http://3.81.143.40" ;
        RestAssured.port = 8000 ;
        RestAssured.basePath = "/api" ;
    }

    @DisplayName("Post request with String as body")
    @Test
    public void testPostWithStringBody(){

        // lets try to get random names rather than same ADAM each time

        Faker faker = new Faker();
        String randomName = faker.name().firstName();
        System.out.println("randomName = " + randomName);

        String bodyString = "{\n" +
                "\"name\"  : \"" + randomName+ "\",\n" +
                "  \"gender\": \"Female\",\n" +
                "  \"phone\": 6234567890\n" +
                "}";
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(bodyString).
        when()
                .post("/spartans").
        then()
                .log().all()
                .statusCode(201)
                .body("data.name", is(randomName));
    }
    @DisplayName("Posting with external json file")
    @Test
    public void testPostWithExternalFile(){

        File file1 = new java.io.File("spartan.json");

        // create a file object that can point to spartan.json you just added
        // so we can use this body in post request

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(file1).
        when()
                .post("/spartans").
        then()
                .log().all()
                .statusCode(201)
                .body("data.name", is("New Name"));
    }

    @DisplayName("Posting with Map object as body")
    @Test
    public void testPostWithMapAsBody(){

        // please add dependency jackson-databind

        // create a Map<String, Object> as HashMap
        Map<String, Object> bodyMap = new LinkedHashMap<>();

        // add name, gender, phone
        bodyMap.put("name","Vincent");
        bodyMap.put("gender", "Male");
        bodyMap.put("phone", 3466346789L);

        System.out.println("bodyMap = " + bodyMap);

        given().log().all()
                .contentType(ContentType.JSON)
                .body(bodyMap). // jackson-data-bind turn your java map object to json here
        when()
                .post("/spartans").
        then()
                .log().all()
                .statusCode(201)
                .body("data.name", is(bodyMap.get("name")));


    }
}
