import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static List<String>         ingredients     = new ArrayList<>();
    public static List<String>         parameters      = new ArrayList<>();
    public static Map<String, Integer> currentCombo    = new HashMap<>();
    public static Map<String, Integer> maxScoreCombo   = new HashMap<>();
    public static Map<String, Integer> calorieCombo    = new HashMap<>();
    public static Map<String, String>  combos          = new HashMap<>();
    public static int                  teaspoons       = 0;
    public static int                  maxScore        = 0;
    public static int                  maxCalorieScore = 0;

    public static void main(String[] args) {

        populateLists();
        combineIngredients();

        // Print the results to screen:
        // PART 1:
        System.out.printf("MAX SCORE: %d\n", maxScore);
        currentCombo = maxScoreCombo;
        printCombo();
        System.out.printf("\n");

        // PART 2:
        System.out.printf("MAX SCORE (500 cal): %d\n", maxCalorieScore);
        currentCombo = calorieCombo;
        printCombo();

    }

    /*
        Generate all possible combinations of ingredients in amounts
        summing to 100 teaspoons.
        TODO: Should be rewritten using recursion. Hard-coding ingredients reeks
     */
    public static void combineIngredients() {

       for (int i=1; i<100; i++) {

           currentCombo.put("Frosting", i);
            for (int j=1; j<100-i; j++) {

                currentCombo.put("Butterscotch", j);
                for (int k=1; k<100-(i+j); k++) {

                    currentCombo.put("Candy", k);
                    for (int l=100-(i+j+k); l<100; l++) {
                        teaspoons = i+j+k+l;
                        currentCombo.put("Sugar", l);
                        if (teaspoons == 100) {
                            getScore();
                        }
                    }
                }
            }
       }
    }

    /*
        Print the current combination of ingredients and volumes to screen
     */
    public static void printCombo() {
        for (String ing : currentCombo.keySet()) {
            System.out.printf("%s: %d, ", ing, currentCombo.get(ing));
        }
        System.out.printf("\n");
    }

    /*
        Calculate the score of the current combination of ingredients and volumes
     */
    public static int getScore() {
        int score;
        int calories = 0;
        int k = 0;
        int[] scores = new int[4];
        for (String param : parameters) {
            if (!param.equals("calories")) {
                int paramScore = 0;
                for (String ing : ingredients) {
                    paramScore += getValue(ing, param) * currentCombo.get(ing);
                }
                scores[k] = ((paramScore > 0) ? paramScore : 0);
                k++;
            }
            else {
                calories = 0;
                for (String ing : ingredients) {
                    calories += getValue(ing, param) * currentCombo.get(ing);
                }
            }
        }
        score = scores[0];
        for (int i=1; i<scores.length; i++) {
            score *= scores[i];
        }
        if (score > maxScore) {
            recordCombo(currentCombo, maxScoreCombo);
            maxScore = score;
        }
        if (calories == 500) {
            if (score > maxCalorieScore) {
                recordCombo(currentCombo, calorieCombo);
                maxCalorieScore = score;
            }
        }
        return score;
    }

    /*
        Record recipe state to a class variable
     */
    public static void recordCombo(Map<String, Integer> src, Map<String, Integer> dest) {
        for (String ing : src.keySet()) {
            dest.put(ing, src.get(ing));
        }
    }

    /*
        Given an ingredient and parameter, extract the coefficient from
        the input file.
        RETURNS coefficient
     */
    public static int getValue(String ingredient, String param) {
        int value = -1;
        Pattern pattern = Pattern.compile(".*?("+param+") ([-0-9]{1,}).*?");
        Matcher matcher = pattern.matcher(combos.get(ingredient));
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(2));
        }
        return value;
    }

    /*
        Parse the input file and populate class variables with data.
     */
    public static void populateLists() {
        List<String> instructions;
        Path path = Paths.get("./input");
        try {
            instructions = Files.readAllLines(path);
            Iterator<String> i = instructions.iterator();
            while(i.hasNext()) {
                String line = i.next();
                List<Integer> values = new ArrayList<>();
                Pattern pattern = Pattern.compile("([A-Z]{1}[a-z]{1,}):.*?");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String ingredient = matcher.group(1);
                    if (!ingredients.contains(matcher.group(1))) {
                        ingredients.add(ingredient);
                        currentCombo.put(ingredient,0);
                    }
                    String[] params = line.replaceAll("([A-Za-z]{1,}: )","").split(", ");
                    for (String param : params) {
                        Pattern regex = Pattern.compile("([a-z]{1,}) ([0-9]{1,})");
                        Matcher matches = regex.matcher(param);
                        if (matches.find()) {
                            if (!parameters.contains(matches.group(1))) {
                                parameters.add(matches.group(1));
                            }
                        }
                    }
                    combos.put(ingredient, line.replaceAll("([A-Za-z]{1,}: )",""));
                }
            }

        } catch (IOException e) {
            System.out.println("IOException raised. Did you remember to create input file?");
            e.printStackTrace();
        }
    }

}
