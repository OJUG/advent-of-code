import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

/*
  PROBLEM: Given a list of strings, identify the total number of strings that
  match a set of pre-defined rules.
  STRATEGY: Write a method for each rule. Read each line from the provided input
  and pass the line through method. A line that returns true for all three
  methods increments a counter to indicate another successful match.
  RETURN the number of successful matches.
*/

public class Main {

    public static void main(String[] args) {

        int niceLinesOne = 0;
        int niceLinesTwo = 0;
        List<String> lines;
        Path path = Paths.get("./input");

        try {
            lines = Files.readAllLines(path);
            Iterator<String> i = lines.iterator();

            while(i.hasNext()) {
                String line = i.next();

                // Part 1:
                if (niceVowels(line) && repeatedChars(line) && doesNotContainChars(line)) {
                    niceLinesOne++;
                }

                // Part 2:
                if (twoNonOverlappingPairs(line) && oneRepeatLetter(line)) {
                    niceLinesTwo++;
                }

            }
            System.out.printf("Part 1 nice lines: %d, Part 2 nice lines: %d.\n", niceLinesOne, niceLinesTwo);
        } catch (IOException e) {
            System.out.println("IOException raised. Did you create the input file?");
            e.printStackTrace();
        }

    }

    /*
        Accepts a string and looks for the characters a,e,i,o,u.
        Returns true if at least 3 of those characters are found.
     */
    public static boolean niceVowels(String line) {
        int vowels = 0;
        for (int j=0; j < line.length(); j++) {
            String chr = line.substring(j,j+1);
            if (chr.equals("a") || chr.equals("e") || chr.equals("i") || chr.equals("o") || chr.equals("u")) {
                vowels++;
            }
            if (vowels == 3) {
                break;
            }
        }
        return (vowels >= 3);
    }

    /*
        Accepts a string and looks for any character repeated twice.
        Returns true if at least 1 character is repeated.
     */
    public static boolean repeatedChars(String line) {
        for (int j=0; j < line.length()-1; j++) {
            String chr1 = line.substring(j,j+1);
            String chr2 = line.substring(j+1,j+2);
            if (chr1.equals(chr2)) {
                return true;
            }
        }
        return false;
    }

    /*
        Accepts a string and looks for substrings to avoid.
        Returns true if no substrings are found.
     */
    public static boolean doesNotContainChars(String line) {
        for (int j=0; j < line.length()-1; j++) {
            String chr = line.substring(j,j+2);
            if (chr.equals("ab") || chr.equals("cd") || chr.equals("pq") || chr.equals("xy")) {
                return false;
            }
        }
        return true;
    }

    /*
        Accepts a line from the list and looks for a 2 character substring that is
        repeated later in the string.
        RETURN true if a matching substring is found
     */
    public static boolean twoNonOverlappingPairs(String line) {
        String pair;
        for (int i=0;i<line.length()-1;i++) {
            pair = line.substring(i,i+2);
            for (int j=i+2; j<line.length()-1;j++) {
                String testPair=line.substring(j,j+2);
                if (pair.equals(testPair)) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
        Accepts a line from the list and looks for a 3-character substring where the
        first and last character are the same.
        RETURN true if a matching substring is found.
     */
    public static boolean oneRepeatLetter(String line) {
        String trip;
        for (int i=0;i<line.length()-2;i++) {
            trip = line.substring(i,i+3);
            if (trip.substring(0,1).equals(trip.substring(2,3))) {
                return true;
            }
        }
        return false;
    }
}
