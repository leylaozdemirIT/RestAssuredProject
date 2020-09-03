package day01;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


// Hamcrest library is a assertion library
// to aim make the test more human readable
// using lots of human readable matchers like something is(somethingelse)
// Most importantly restassured use hamcrest matcher
//when we chain multiple rest assured methods

public class Practice2 {

    @Test
    public void test1(){

        // assert 5+4 is 9
        int num1 = 5 ;
        int num2 = 4 ;
        // we need these two import for this to work
//        import static org.hamcrest.MatcherAssert.assertThat;
//        import static org.hamcrest.Matchers.*;
        // Hamcrest already come with RestAssured dependency

        // hamcrest library use the assertThat method for all assertions
        // hamcrest use built in matchers to do assertion
        // couple common ones are :
            //  is( some value )
            // equalTo( some value)
            //  or optionally   is ( equalTo(some value) )
            // not

        assertThat( num1 + num2 ,  is(9)   );
        assertThat( num1 + num2 ,  equalTo(9)   );
        assertThat( num1 + num2 ,  is(equalTo(9) ) );

        // not (value)
        // is ( not(some value) )
        // not ( equalTo(11) )
        assertThat( num1 + num2 ,  not(10)   );
        assertThat( num1 + num2 ,  is (not(10) ) );
        assertThat( num1 + num2 ,  not( equalTo(11)) );

        // save your first name and last name into 2 variables
        // test the concatenation

        String first_name = "Leyla";
        String last_name = "Ozdemir";

        assertThat(first_name + " " + last_name, is("Leyla Ozdemir") );
        assertThat( first_name + " " + last_name ,  equalTo("Leyla Ozdemir")   );
        assertThat( first_name + " " + last_name ,  is(equalTo("Leyla Ozdemir") ) );

        // String matchers
        // equalToIgnoringCase
        // equalToCompressingWhiteSpace
        // containsString, endsWith, startsWith - test string matching

        // how to check without in caseInsensitive manner
        assertThat(first_name, equalToIgnoringCase("leyla") );
        // how to ignore all white spaces
        assertThat(first_name, equalToCompressingWhiteSpace("leyla") );

        assertThat(first_name, endsWith("la"));
        assertThat(last_name, startsWith("Oz"));
        assertThat(last_name, containsString("d"));

    }
    @DisplayName("Suppor for collection")
    @Test
    public void test2(){

        List<Integer> numList = Arrays.asList(11,44,3,55,88,5) ;
        // checking the list size is 6
        assertThat(numList, hasSize(6)  );
        // checking the list contains 11
        assertThat(numList, hasItem(11) );
        // has items is used to check multiple items : 11, 3, 5
        assertThat(numList, hasItems(11,3,5) );
//
        // checking the list contains all the items in exact order : 11,44,3,55,88,5
        assertThat(numList, contains(11,44,3,55,88,5 )   );
        // checking the list contains all the items in any order  : 11, 44, 55,3,88
        assertThat(numList,  containsInAnyOrder(11, 44, 55,3,88,5 )   );


    }

}
