import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

/*
  PROBLEM: Given a set of instructions for moving between floors, determine
  the final floor that is arrived at by parsing the input.
  STRATEGY: Read instructions from an input file (allow it to be multiple
  lines). Treat each line in the input file as a string. Iterate over each
  character in the string and increment/decrement a counter according to the
  given rules. Ignore undefined operations.
  RETURN the final value of the counter.
*/

public class Main {

    public static void main(String[] args) {

        int floor = 0;
        List<String> directions;
        Path path = Paths.get("./input");
        try {
            directions = Files.readAllLines(path);
            Iterator<String> i = directions.iterator();

            while(i.hasNext()) {
                String direction_set = i.next();

                for (int j=0; j < direction_set.length(); j++) {
                    String floor_move = direction_set.substring(j,j+1);
                    if (floor_move.equals("(")) {
                        floor++;
                    }
                    else if (floor_move.equals(")")) {
                        floor--;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("IOException raised. Did you create the input file?");
            e.printStackTrace();
        }
        System.out.printf("Santa ends up on floor %d.\n", floor);
    }
}
