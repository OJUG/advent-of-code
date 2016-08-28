import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;

/*
    PROBLEM: Given a list of lines with escape characters, determine how many characters will be
    stored in memory, and how many characters are needed to store those strings on disk. Return
    the difference.

    STRATEGY: Read each line in the input file, and count how many characters it has. Then,
    parse the line and look for escape sequence patterns ("\\", "\"" and "\x.."). Each of these
    escape sequences represent one letter, so replace them with a single, arbitrary character.
    Count the characters in the translated line, and subtract two (for the double quotes, which
    won't be stored in memory). Maintain a counter for both the string literals and memory chars.
    Once all lines have been processed, compute the difference between the literals and memory
    chars, and return the result.
 */

public class Main {

    public static void main(String[] args) {

        int total_str_chars = 0;
        int total_mem_chars = 0;
        int total_enc_mem_chars = 0;
        List<String> instructions;
        Path path = Paths.get("./input");
        try {
            instructions = Files.readAllLines(path);
            Iterator<String> i = instructions.iterator();

            while(i.hasNext()) {
                String instruction = i.next();

                // Part 1:
                String new_instruction = transformString(instruction);
                int str_chars = instruction.length();
                int mem_chars = new_instruction.length() - 2;
                total_mem_chars += mem_chars;
                total_str_chars += str_chars;

                // Part 2:
                String enc_instruction = encodeString(instruction);
                int enc_mem_chars = enc_instruction.length();
                total_enc_mem_chars += enc_mem_chars;
            }


        } catch (IOException e) {
            System.out.println("IOException raised. Did you remember to create input file?");
            e.printStackTrace();
        }

        System.out.printf("Part 1 answer: %d.\n", (total_str_chars - total_mem_chars));
        System.out.printf("Part 2 answer: %d.\n", (total_enc_mem_chars - total_str_chars));

    }

    /*
        Given a line from the input, translate the escape sequences. We don't really
        care what they escape to for the purposes of this challenge, so we simply
        replace them with a single character.
        RETURNS the transformed string.
     */
    public static String transformString(String instruction) {
        return instruction.
                replaceAll("\\\\\\\\",Matcher.quoteReplacement("A")).
                replaceAll("\\\\x[a-zA-Z0-9]{2}", "B").
                replaceAll("\\\\\"","C");
    }

    /*
        Given a line from the input, encode it.
        RETURNS the encoded string
     */
    public static String encodeString(String instruction) {
        String transformedString = instruction.
                replaceAll("\\\\", Matcher.quoteReplacement("___")).
                replaceAll("\"", "\\\\\"").
                replaceAll("\'", "\\\\\'").
                replaceAll("___", "\\\\\\\\");

        return String.format("\"%s\"", transformedString);
    }

}
