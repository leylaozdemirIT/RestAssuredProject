package day12;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utility.ConfigurationReader;

import java.util.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SpartanXML_Test {

    @BeforeAll
    public static void init() {
        RestAssured.baseURI = ConfigurationReader.getProperty("spartan1.base_url");
        RestAssured.basePath = "/api";

    }
    @Test
    public void testXML_Response(){
        given()
                .accept(ContentType.XML).
        when()
                .get("/spartans").
        then()
                .contentType(ContentType.XML)
                .statusCode(200)
                .body("List.item[0].name",is("Leyla Ozdemir"))
                .body("List.item[0].gender",is("Female"))
                .body("List.item[0].id",is("104"));
    }
    //XMLPath xp = response.xmlPath();

    @DisplayName("XMLPath object to extract the data out")
    @Test
    public void testSpartanExtractData(){

        Response response = given()
                .accept(ContentType.XML).
        when()
                .get("/spartans");

        // getting XMLPath object just like we did for JsonPath object
        XmlPath xp = response.xmlPath();

        int firstID = xp.getInt("List.item[0].id");
        System.out.println("firstID = " + firstID);

        String firstName = xp.getString("List.item[0].name");
        System.out.println("firstName = " + firstName);

        long firstPhone = xp.getLong("List.item[0].phone");
        System.out.println("firstPhone = " + firstPhone);

        // get all the ids and store it into the list
        List<Integer> allIDs = xp.getList("List.item.id", Integer.class);
        System.out.println("allIDs = " + allIDs);
        System.out.println(allIDs.size());

        // assert the list size is some number
        // assert above list has items 104,105,106
        // import static org.hamcrest.MatcherAssert.assertThat;
        // practice hamcrest matcher
        assertThat(allIDs, hasSize(120));

        // when we got the List<Integer> without specifying what type in getList method
        // somehow it can not decide the in the Hamcrest assertThat method it's a List<Integer>
        // so the fix was to provide class type for the getList method to make it obvious
        // like this List<Integer> idList = xp.getList("List.item.id", Integer.class);
        assertThat( allIDs, hasItems(104,105,106));

        // Get a List of phone numbers
        // first check the size is 120
        List<Long> phoneNums = xp.getList("List.item.phone", Long.class);
        System.out.println("phone Nums = " + phoneNums);
        assertThat(phoneNums, hasSize(120));
        assertThat(phoneNums, hasItems(1231231231L,1234567890L,3584128232L));

        // optionally
        // check every item is greater than 10000000
        // How to check everytime in the collection match certain criteria
        assertThat( 5, greaterThan(3) );
        assertThat(phoneNums,everyItem( greaterThan(1000000000L) ) );

        // Get a List of String from the names
        // find out how many unique names you have
        List<String> allNames = xp.getList("List.item.name");
        System.out.println("allNames = " + allNames);
        // Set interface define collection of unique elements
        // creating a HashSet object by copying everything from List, duplicates are auto-removed
        Set<String> uniqueNames = new HashSet<>(allNames);
        System.out.println("uniqueNames = " + uniqueNames);

        System.out.println("allNames.size() = " + allNames.size());
        System.out.println("uniqueNames.size() = " + uniqueNames.size());

    }

}
