import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static Map<String, Map<String, Integer>> seating = new HashMap<String, Map<String, Integer>>();

    public static void main(String[] args) {
        List<String> instructions;
        Path path = Paths.get("./input");
        try {
            instructions = Files.readAllLines(path);
            Iterator<String> i = instructions.iterator();

            while(i.hasNext()) {
                String line = i.next();
                processSeating(line);
            }

            int maxWithoutYou = maxHappiness(false);
            int maxWithYou = maxHappiness(true);

            System.out.printf("Maximum happiness (without you) is %d.\n", maxWithoutYou);
            System.out.printf("Maximum happiness (with you) is %d.\n", maxWithYou);
            if (maxWithoutYou > maxWithYou) {
                System.out.printf("Aww, that's sad... :(\n");
            }
        } catch (IOException e) {
            System.out.println("IOException raised. Did you remember to create input file?");
            e.printStackTrace();
        }

    }

    public static void processSeating(String line) {
        Pattern pattern = Pattern.compile("([A-Za-z]{1,}) .*?(gain|lose) ([0-9]{1,}).*?([A-Z]{1}[a-z]{1,})\\.");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String op = (matcher.group(2).equals("gain")) ? "+" : "-";
            int val = Integer.parseInt(op+matcher.group(3));
            if (seating.get(matcher.group(1)) == null) {
                Map<String, Integer> companion = new HashMap<String, Integer>();
                companion.put(matcher.group(4), val);
                seating.put(matcher.group(1), companion);
            }
            else {
                seating.get(matcher.group(1)).put(matcher.group(4), val);
            }
        }
    }

    public static int maxHappiness(boolean you) {
        int maxHappy = 0;
        for (int j=0; j<factorial(seating.keySet().size()); j++) {
            int happiness = 0;
            List<String> guests = getGuests();
            if (you) {guests.add("You");}
            Collections.shuffle(guests);
            guests.add(guests.get(0));
            for (int k=0; k<guests.size()-1;k++) {
                String p1 = guests.get(k);
                String p2 = guests.get(k+1);
                happiness += (p1.equals("You") || p2.equals("You")) ? 0 : seating.get(p1).get(p2);
                happiness += (p1.equals("You") || p2.equals("You")) ? 0 : seating.get(p2).get(p1);
            }
            maxHappy = (happiness > maxHappy) ? happiness : maxHappy;
        }
        return maxHappy;
    }

    public static List<String> getGuests() {
        List<String> guests = new ArrayList<String>();
        for (String guest : seating.keySet()) {
            guests.add(guest);
        }
        return guests;
    }

    public static int factorial(int number) {
        if (number > 1) {
            return number * factorial(number-1);
        }
        return number;
    }

}
