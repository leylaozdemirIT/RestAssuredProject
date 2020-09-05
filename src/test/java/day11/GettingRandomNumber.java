package day11;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GettingRandomNumber {

    @Test
    public void  testRandom(){

        // Random class is coming form java.util package and can provide some random numbers in different type
        // First we need to create an object

        Random r = new Random();
        int randomNumber = r.nextInt(10);
        System.out.println("randomNumber = " + randomNumber);

        List<String> names = Arrays.asList("Derin","Leyla","Onur","Leyla","Billur","Cici");
        // get a random name from the list each time
        System.out.println("Lucky name is = " + names.get(randomNumber));
    }
}
