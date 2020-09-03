package day09;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
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
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class SpartanPostRequestExtractingSpecTest {

    static RequestSpecification validPostRequestSpec;
    static ResponseSpecification validPostResponseSpec;

    @BeforeAll
    public static void init() {
        RestAssured.baseURI = "http://54.160.106.84";
        RestAssured.port = 8000;
        RestAssured.basePath = "/api";
        Spartan randomSp = createRandomSpartanObject();

        validPostRequestSpec = given()
                .auth().basic("admin","admin")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(randomSp)
                .log().all();
        ResponseSpecBuilder resSpecBuilder = new ResponseSpecBuilder() ;
        validPostResponseSpec = resSpecBuilder
                .expectStatusCode(201)
                .expectHeader("Date", notNullValue(String.class))
                .log(LogDetail.ALL)
                .expectBody("success", is("A Spartan is Born!"))
                .expectBody("data.name",is(randomSp.getName()))
                .expectBody("data.gender",is(randomSp.getGender()))
                .expectBody("data.phone",is(randomSp.getPhone()))
                .expectBody("data.id",notNullValue()).build();
    }

    @DisplayName("Extracting the requestSpec and responseSpec practice")
    @Test
    public void test () {

        given()
                .spec(validPostRequestSpec).
        when()
                .post("/spartans").
        then()
                .spec(validPostResponseSpec);

        }
        public static Spartan createRandomSpartanObject(){
            Faker faker = new Faker();
            String name = faker.name().firstName();
            String gender = faker.demographic().sex();
            // always getting long number outside range of int to avoid errors
            long phone    = faker.number().numberBetween(5000000000L,9999999999L);
            Spartan randomSpartanObject = new Spartan(name,gender,phone);
            System.out.println("Created Random Spartan Object : " + randomSpartanObject);
            return randomSpartanObject ;

        }
    }

