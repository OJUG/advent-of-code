import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
    PROBLEM: Given a number of cities and the distance between them, calculate the
    shortest route that passes through all cities exactly once.

    NOTES: This problem is more generally known as the Traveling Salesman Problem.
    Basically, given a finite number of nodes, determine the shortest possible path
    that connects all nodes exactly once. The problem is still unsolved for the
    general case, so the only way to be absolutely certain of the answer is to
    directly compute all possible paths (the total number of routes is equal to
    n!, where n is the number of nodes). Due to geometrical growth, finding a
    solution quickly becomes infeasible (there are 40,320 possible routes for 8
    nodes, but 362,880 for 9 nodes, and 3,628,800 possible routes for 10 nodes).

    STRATEGY: Rather than write a program that parses out and traces all possible
    paths and determining which is the shortest, I'm going to take a lazier, but
    more resource-efficient approach: collect all nodes into a list, shuffle that
    list, then join the elements to form a randomized route. Calculate the distance
    traveled in that route and compare it against a class variable. If it's lower,
    set the class variable to that distance and record the route. Otherwise move on.
    I'll do this for n! iterations, which should give _at least_ a 63% chance of
    hitting the correct route.

*/

public class Main {

    public static int minDistance;
    public static int maxDistance;
    public static String minRoute;
    public static String maxRoute;
    public static Map<String, Integer> destinations = new HashMap<String, Integer>();

    public static void main(String[] args) {

        List<String> legs;
        Path path = Paths.get("./input");
        try {

            legs = Files.readAllLines(path);
            Iterator<String> i = legs.iterator();
            while(i.hasNext()) {
                populateCities(i.next());
            }

            List<String> allCities = getAllCities();
            for (int j=0; j<factorial(1+allCities.size());j++) {
                Collections.shuffle(allCities);
                calculateRoute(allCities);
            }

            System.out.printf("Shortest possible route: %d mi (%s)\n", Main.minDistance, Main.minRoute);
            System.out.printf("Longest possible route: %d mi (%s)\n", Main.maxDistance, Main.maxRoute);


        } catch (IOException e) {
            System.out.println("IOException raised. Did you remember to create input file?");
            e.printStackTrace();
        }

    }

    /*
        Parse each line in the input file and populate a class variable, destinations,
        with the distance between each location.
        RETURN nothing.
     */
    public static void populateCities(String leg) {
        int distance = 0;
        String[] cities = new String[2];
        Pattern pattern = Pattern.compile("([a-zA-Z]{1,}) to ([a-zA-Z]{1,}) = ([0-9]{1,})");
        Matcher matcher = pattern.matcher(leg);
        if (matcher.find())
        {
            cities[0] = matcher.group(1);
            cities[1] = matcher.group(2);
            distance  = Integer.parseInt(matcher.group(3));
            Main.destinations.put(cities[0]+"-"+cities[1], distance);
            Main.destinations.put(cities[1]+"-"+cities[0], distance);
        }
        return;
    }

    /*
        Parse the class variable `destinations` for a list of unique locations.
        RETURN a list of all cities to be visited.
     */
    public static List<String> getAllCities() {
        String city;
        List<String> cities = new ArrayList<String>();
        for (String leg : Main.destinations.keySet()) {
            city = getOrigination(leg);
            if (!cities.contains(city)) {
                cities.add(city);
            }
        }
        return cities;
    }

    /*
        Given a leg between two cities, find the origination city.
        RETURN the origination city.
    */
    public static String getOrigination(String leg) {
        Pattern pattern = Pattern.compile("([a-zA-Z]{1,})-.*?");
        Matcher matcher = pattern.matcher(leg);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    /*
        Given a leg between two cities, find the destination city.
        RETURN the destination city.
    */
    public static String getDestination(String leg) {
        Pattern pattern = Pattern.compile("^.*?-([a-zA-Z]{1,})");
        Matcher matcher = pattern.matcher(leg);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    /*
        Given a list of all cities (nodes), shuffle the list and treat it like
        a route. Calculate the distance between all cities in the route.
        If the distance is the lowest measured, mutate the class variables for
        shortest route and distance.
    */
    public static void calculateRoute(List<String> cities) {
        String route = "";
        String leg = "";
        int distance = 0;
        int j=2;
        for (int i=0; i<cities.size()-1;i++) {
            leg = cities.get(i)+"-"+cities.get(i+1);
            distance += Main.destinations.get(leg);
            route += cities.get(i)+"-";
        }
        route += cities.get(cities.size()-1);
        if (Main.minDistance == 0 || distance < Main.minDistance) {
            Main.minRoute = route;
            Main.minDistance = distance;
        } else if (Main.minDistance == 0 || distance > Main.maxDistance) {
            Main.maxRoute = route;
            Main.maxDistance = distance;
        }
    }

    /*
        Given an integer, recursively multiply it by one less than
        itself until it's been reduced to 1.
        RETURN an factorialized integer.
    */
    public static int factorial(int number) {
        if (number > 1) {
            return number * factorial(number-1);
        }
        return number;
    }
}
