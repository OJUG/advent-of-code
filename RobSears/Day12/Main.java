import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                total += sumNumbers(getNumbers(instruction));
            }

            System.out.printf("Sum of all numbers: %d\n", total);

        } catch (IOException e) {
            System.out.println("IOException raised. Did you remember to create input file?");
            e.printStackTrace();
        }

    }

    public static ArrayList<Integer> getNumbers(String instruction) {
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        Pattern pattern = Pattern.compile("([-0-9]{1,})");
        Matcher matcher = pattern.matcher(instruction);
        while (matcher.find()) {
            numbers.add(Integer.parseInt(matcher.group(1)));
        }
        return numbers;
    }

    public static int sumNumbers(ArrayList<Integer> numbers) {
        int total = 0;
        for (Integer number : numbers) {
            total += number;
        }
        return total;
    }
}
