import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static Map<String, Integer> competitors = new HashMap<String, Integer>();
    public static Map<String, Integer> positions   = new HashMap<String, Integer>();
    public static int    raceDuration  = 2503;
    public static int    maxDistance   = 0;
    public static String maxCompetitor = "";

    public static void main(String[] args) {

        for (int i=1;i<=raceDuration;i++) {
            raceInterval(i);
        }
        String winner = getWinner();
        System.out.printf("Part 1 winner: %s, with a score of %d!\n", maxCompetitor, maxDistance);
        System.out.printf("Part 2 winner: %s, with a score of %d!\n", winner, competitors.get(winner));

    }

    /*
        Given a time in seconds, determine the positions of all competitors, and assign
        points to the leading competitor(s).
        RETURNS void.
     */
    public static void raceInterval(int seconds) {
        List<String> instructions;
        Path path = Paths.get("/Users/rob/Projects/Java/Day14/src/com/company/input");
        try {
            instructions = Files.readAllLines(path);
            Iterator<String> i = instructions.iterator();
            maxCompetitor = "";
            maxDistance = 0;
            while(i.hasNext()) {
                String line = i.next();
                String competitor = getCompetitor(line);
                int delta = distanceTraveled(seconds, line);
                int distance = delta + positions.get(competitor);
                maxCompetitor = (distance > maxDistance) ? competitor: maxCompetitor;
                maxDistance   = (distance > maxDistance) ? distance: maxDistance;
                positions.put(competitor, distance);
            }

            for (String competitor : positions.keySet()) {
                if (positions.get(competitor) >= maxDistance) {
                    competitors.put(competitor, competitors.get(competitor)+1);
                }
            }

        } catch (IOException e) {
            System.out.println("IOException raised. Did you remember to create input file?");
            e.printStackTrace();
        }
    }

    /*
        Determine which competitor is winning, according to the current
        snapshot.
        RETURNS a string representing the winning competitor.
     */
    public static String getWinner() {
        int maxPoints = 0;
        String maxCompetitor = "";
        for (String competitor : competitors.keySet()) {
            if (competitors.get(competitor) > maxPoints) {
                maxPoints = competitors.get(competitor);
                maxCompetitor = competitor;
            }
        }
        return maxCompetitor;
    }

    /*
        Determine how far the competitor has traveled at the end of
        X seconds.
        RETURN the distance traveled in X seconds.
     */
    public static int distanceTraveled(int seconds, String line) {
        List<Integer> values = getValues(line);
        int distance = 0;
        if ((values.get(1)+values.get(2)) < seconds) {
            seconds = seconds % (values.get(1)+values.get(2));
        }
        if (seconds <= values.get(1) && seconds > 0) {
            distance += values.get(0);
        }
        return distance;
    }

    /*
        Read a line from the input file and extract a list of values representing
        the competitor's top speed, flight time and rest time.
        RETURNS a list of integers representing flight characteristics.
     */
    public static List<Integer> getValues(String line) {
        List<Integer> values = new ArrayList<>();
        Pattern pattern = Pattern.compile("([A-Z]{1}[a-z]{1,}) c.*?([0-9]{1,}) km.*?([0-9]{1,}) s.*?([0-9]{1,}) s.*");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            // If there is no data on the competitor's points or position,
            // add them to the class variables:
            if (!competitors.keySet().contains(matcher.group(1))) {
                competitors.put(matcher.group(1),0);
            }
            if (!positions.keySet().contains(matcher.group(1))) {
                positions.put(matcher.group(1),0);
            }
            values.add(Integer.parseInt(matcher.group(2)));
            values.add(Integer.parseInt(matcher.group(3)));
            values.add(Integer.parseInt(matcher.group(4)));
        }
        return values;
    }

    /*
        Given a line from the input file, extract the name of the competitor.
        RETURNS a string with the competitor's name.
     */
    public static String getCompetitor(String line) {
        Pattern pattern = Pattern.compile("([A-Z]{1}[a-z]{1,}) c.*?");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

}
