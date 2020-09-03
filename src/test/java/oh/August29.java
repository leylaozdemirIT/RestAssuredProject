package oh;

import io.restassured.response.Response;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static io.restassured.RestAssured.*;

public class August29 {

    public static void main(String[] args) {

        String URL = "http://api.zippopotam.us/us/90210";
        baseURI = "http://api.zippopotam.us";

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter country code");
        String country = scanner.next();
        System.out.print("Enter postal code: ");
        String postalCode = scanner.next();
        // pattern checker , to check if it matches only following format
        // \\d+ - string can contain only digits of any length
        if(!postalCode.matches("\\d+")){
            throw new RuntimeException("Wrong postal code");
        }

        Response response = given()
                .pathParam("country", country)
                .pathParams("zip", postalCode).
              //  .pathParams("country","us", "zip", "90210"). both params in 1 line
        when()
               .get("/{country}/{zip}").prettyPeek();
            //    .get("/us/90210").prettyPrint(); second way shorter

        String countryValue = response.jsonPath().getString("country");

        System.out.println("Country: " + countryValue);

        List<Map<String, Object>> places = response.jsonPath().getList("places");
        List<Place> places2 = response.jsonPath().getList("places", Place.class);

        System.out.println(places2);

        for(Map<String, Object> place : places){
            place.forEach((key,value) -> {
                System.out.println(key + " : " + value);
            });
            System.out.println();
        }


    }
}
