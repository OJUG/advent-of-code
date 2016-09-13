import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static Map<String, Integer> ticker = new HashMap<>();
    public static int partOneAnswer = 0;
    public static int partTwoAnswer = 0;

    public static void main(String[] args) {

        populateTicker();
        parseInput();

        System.out.printf("Part 1 answer: Sue %d.\n", partOneAnswer);
        System.out.printf("Part 2 answer: Sue %d.\n", partTwoAnswer);

    }

    /*
        Create a hashmap of the ticker findings so we can compare the input
        with an object in memory.
     */
    public static void populateTicker() {
        List<String> parameters;
        Path path = Paths.get("./ticker");
        try {
            parameters = Files.readAllLines(path);
            Iterator<String> i = parameters.iterator();
            while(i.hasNext()) {
                String[] parameter = i.next().split(": ");
                ticker.put(parameter[0], Integer.parseInt(parameter[1]));
            }

        } catch (IOException e) {
            System.out.println("IOException raised. Did you remember to create ticker file?");
            e.printStackTrace();
        }
    }

    /*
        Parse the input file and test each line against the ticker.
     */
    public static void parseInput() {
        List<String> sues;
        Path path = Paths.get("./input");
        try {
            sues = Files.readAllLines(path);
            Iterator<String> i = sues.iterator();
            while(i.hasNext()) {
                String line = i.next();
                Pattern pattern = Pattern.compile("([a-z]{1,}: [0-9]{1,})");
                Matcher matcher = pattern.matcher(line);
                boolean testMatchOne = true;
                boolean testMatchTwo = true;
                while (matcher.find()) {
                    String[] q = matcher.group().split(": ");
                    if (q[0].equals("cats") || q[0].equals("trees")) {
                        if (ticker.get(q[0]) >= Integer.parseInt(q[1])) {
                            testMatchTwo = false;
                        }
                    }
                    else if (q[0].equals("pomeranians") || q[0].equals("goldfish")) {
                        if (ticker.get(q[0]) <= Integer.parseInt(q[1])) {
                            testMatchTwo = false;
                        }
                    }
                    else {
                        if (ticker.get(q[0]) != Integer.parseInt(q[1])) {
                            testMatchTwo = false;
                        }
                    }
                    if (ticker.get(q[0]) != Integer.parseInt(q[1])) {
                        testMatchOne = false;
                    }
                }
                if (testMatchOne) {
                    partOneAnswer = getNumber(line);
                }
                if (testMatchTwo) {
                    partTwoAnswer = getNumber(line);
                }
            }

        } catch (IOException e) {
            System.out.println("IOException raised. Did you remember to create input file?");
            e.printStackTrace();
        }
    }

    /*
        Given a line from the input file, get the "Sue number".
        RETURNS an Integer representing the Sue number.
     */
    public static Integer getNumber(String line) {
        Pattern pattern = Pattern.compile("([a-z]{1,} [0-9]{1,})");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            String[] nameParts = matcher.group().split(" ");
            return Integer.parseInt(nameParts[1]);
        }
        return 0;
    }
}
