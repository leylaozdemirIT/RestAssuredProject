package Practices;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;

public class HR_Database {

    @BeforeAll
    public static void init(){
        RestAssured.baseURI = "http://3.81.143.40";
        RestAssured.port = 1000;
        RestAssured.basePath = "ords/hr";

    }
    @DisplayName("Testing /employees")
    @Test
    public void testGetOneEmployee (){

        given()
                .log().all()
                .pathParam("employee_id",113).
        when()
                .get("/employees/{employee_id}").
        then()
                .statusCode(200)
                .body("first_name",is("Luis"));
    }

}
