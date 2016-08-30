import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
    PROBLEM: Given a serialized JSON object, sum all integers within the object
    and return the result. Then sum all integers in all objects that don't contain
    the string "red."

    NOTES: The general solution would involve using a JSON parsing library and
    some kind of recursive algorithm for processing the input. That would at least be the
    ideal approach for a production app, as it offers the greatest flexibility and
    extensibility for future developments. However, it's probably overkill for a simple
    code advent, when a more particular algorithm would suffice to meet the
    challenge.

    STRATEGY: The first part of the problem can be solved by simply using regular
    expressions. The JSON object is searched for all integers, which are added to
    an array. That array is then summed to get the answer. The second part of the problem
    can be solved by adding in some simple processing steps. Objects are extracted
    iteratively from the input, and these extracted objects are checked for arrays.
    nested arrays are cleaned by removing any non-numeric characters. The extracted
    object is then checked for the string "red", and dropped from the input if that
    string is found. The end result only contains integers associated to objects that
    don't contain the string "red." The processed result is then searched with regex and
    summed as before.

    RETURNS the answers for parts 1 and 2.
 */

public class Main {

    public static void main(String[] args) {
        int total = 0;
        int nonRed = 0;
        List<String> instructions;
        Path path = Paths.get("./input");
        try {
            instructions = Files.readAllLines(path);
            Iterator<String> i = instructions.iterator();

            while(i.hasNext()) {
                String instruction = i.next();
                total  += sumNumbers(getNumbers(instruction));
                nonRed += sumNumbers(getNumbers(prepObjects(instruction)));
            }

            System.out.printf("Sum of all numbers: %d\n", total);
            System.out.printf("Sum of non-red numbers: %d\n", nonRed);

        } catch (IOException e) {
            System.out.println("IOException raised. Did you remember to create input file?");
            e.printStackTrace();
        }

    }

    /*
        Given a line of JSON, collect all of the integers into an array.
        RETURNS an array of Integers.
     */
    public static ArrayList<Integer> getNumbers(String instruction) {
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        Pattern pattern = Pattern.compile("([-0-9]{1,})");
        Matcher matcher = pattern.matcher(instruction);
        while (matcher.find()) {
            numbers.add(Integer.parseInt(matcher.group(1)));
        }
        return numbers;
    }

    /*
        Given an array of Integers, sum the values.
        RETURNS the sum of all Integers in the array.
     */
    public static int sumNumbers(ArrayList<Integer> numbers) {
        int total = 0;
        for (Integer number : numbers) {
            total += number;
        }
        return total;
    }

    /*
        Given a line from the input representing a JSON object, extract the nested
        objects and process them.
        RETURNS a processed JSON object.
     */
    public static String prepObjects(String instruction) {
        int objStart = 0;
        for (int i=0;i<instruction.length();i++) {
            if (i<instruction.length()) {
                String chr = instruction.substring(i, i + 1);
                if (chr.equals("{")) {
                    objStart = i;
                }
                if (chr.equals("}")) {
                    String object = filterObjects(cleanArray(instruction.substring(objStart,i+1)));
                    instruction = instruction.substring(0, objStart) + sumNumbers(getNumbers(object)) + instruction.substring(i+1, instruction.length());
                    i = 0;
                }
            }
        }
        return instruction;
    }

    /*
        Given a string representing a JSON object, look for arrays and filter the contents
        of any array found.
        RETURNS a string representing the JSON object with "clean" arrays.
     */
    public static String cleanArray(String object) {
        int arrStart = 0;
        for (int i=0;i<object.length();i++) {
            if (i<object.length()) {
                String chr = object.substring(i, i + 1);
                if (chr.equals("[")) {
                    arrStart = i;
                }
                if (chr.equals("]")) {
                    String arr = object.substring(arrStart,i+1);
                    object = object.substring(0, arrStart) + filterNumeric(arr) + object.substring(i+1, object.length());
                    i = 0;
                }
            }
        }
        return object;
    }

    /*
        Given a string representing a JSON array, filter out any character
        that is not a number.
        RETURNS a string representing a numeric-only array.
     */
    public static String filterNumeric(String arr) {
        return arr.replaceAll("[^-0-9]"," ").replaceAll(" {2,}", " ");
    }

    /*
        Given a string representing a JSON object, filter out objects
        according to some rule.
        RETURNS a string meeting the rule.
     */
    public static String filterObjects(String obj) {
        if (obj.indexOf("red") == -1) {
            return obj;
        }
        return "";
    }

}
