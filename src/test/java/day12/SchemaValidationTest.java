package day12;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utility.ConfigurationReader;

import java.io.File;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class SchemaValidationTest {

    @BeforeAll
    public static void init() {
        RestAssured.baseURI = ConfigurationReader.getProperty("spartan1.base_url");
        RestAssured.basePath = "/api";

    }

    @DisplayName("Testing GET /Spartans response against Schema")
    @Test
    public void testAllSpartansSchema() {

        when()
                .get("/spartans").
                then()
                .body(matchesJsonSchemaInClasspath("AllSpartansSchema.json"));

    }

    @DisplayName("Testing GET /Spartans/search response against Schema")
    @Test
    public void testSeachSpartanSchema() {

        // search female in query param and validate response against schema
        given()
                .queryParam("gender", "Female").
                when()
                .get("/spartans/search").
                then()
                .body(matchesJsonSchemaInClasspath("SearchSchema.json"));
    }

    @DisplayName("Testing GET /Spartans response against Schema in rootPath")
    @Test
    public void testAllSpartansSchemaInRootPath() {

        // Create a File object that point to the schema
        // use matchesJsonSchema method that accept a file
        // and do your validation
        File myFile = new File("allSpartans2Schema.json");

        when()
                .get("/spartans").
                then()
                .body(matchesJsonSchema(myFile));
    }
}
