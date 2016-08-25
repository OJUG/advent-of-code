import java.util.ArrayList;
import java.util.List;

/*
    PROBLEM: Given a string of digits, create a new string that expresses the
    previous string in terms of how many times a digit occurs. Repeat an
    arbitrary number of times, each time using the new string as an input
    to be processed.

    STRATEGY: Extract each digit from the input. If the digit is at the start
    of the string or is a repeat digit, increment a counter. Otherwise,
    record the digit and value of the counter to an ArrayList, then reset the
    counter and keep going. This ArrayList can be used to generate a new string.

    RETURNS the length of the final string.
*/

public class Main {

    public static void main(String[] args) {

        // No need for an input file here, we can use CLI args:
        if (args.length < 2) {
            System.out.println("Please specify your puzzle inputs.");
            System.out.println("Usage: java Main '11222311' 40");
            return;
        }

        String input  = args[0];
        int turns     = Integer.parseInt(args[1]);

        System.out.printf("Processing %s %d times.\n", input, turns);
        for (int i=0;i<turns;i++) {
            input = parseInput(input);
        }
        System.out.printf("Output is %d characters long.\n", input.length());
    }

    /*
        Given a string of digits, build an ArrayList representing how many times
        a digit occurs in the string before changing to another digit.
        RETURN a string expressing the input in the form of digits.
    */
    public static String parseInput(String input) {
        String strChar = "";
        int k=0;
        List<String> parsedInput = new ArrayList<String>();
        for (int i=0; i<input.length();i++) {
            if (strChar.equals("") || strChar.equals(input.substring(i,i+1))) {
                strChar = input.substring(i,i+1);
                k++;
            }
            else {
                parsedInput.add(strChar+"-"+k);
                strChar = input.substring(i,i+1);
                k = 1;
            }
        }
        parsedInput.add(strChar+"-"+k);
        return newInput(parsedInput);
    }

    /*
        Given a list expressing how many times a digit was counted in the input,
        process and build a new string.
        RETURN a string expressing the previous input.
    */
    public static String newInput(List<String> parsedInput) {
        String newInput = "";
        for (String chars : parsedInput) {
            String[] pattern = chars.split("-");
            newInput += pattern[1]+pattern[0];
        }
        return newInput;
    }

}
