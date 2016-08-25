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

        int nice_lines = 0;
        List<String> lines;
        Path path = Paths.get("./input");

        try {
            lines = Files.readAllLines(path);
            Iterator<String> i = lines.iterator();

            while(i.hasNext()) {
                String moves = i.next();

                if (niceVowels(moves) && repeatedChars(moves) && doesNotContainChars(moves)) {
                    nice_lines++;
                }

            }

            System.out.printf("Santa's list has %d nice lines.\n", nice_lines);

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
        Accepts a string and looks for certain substrings to avoid.
        Returns true if none of those substrings are found.
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


}
