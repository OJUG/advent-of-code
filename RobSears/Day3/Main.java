import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

/*
  PROBLEM: Given a set of instructions for moving 1 unit at a time over a
  Cartesian plane, determine the unique number of points visited by parsing the
  input.

  STRATEGY: Initialize state variables for (x,y), as well as the total number of
  points visited and the number of unique points visited. Read instructions from
  an input file (allow it to be multiple lines). Treat each line in the input
  file as a string. Iterate over each character in the string and increment or
  decrement either the x or y variables according to the given rules. To
  determine uniqueness, initialize a variable to hold coordinates (arrays of
  integers). When coordinates for a location are constructed, parse the list and
  determine whether it contains those coordinates already. If not, add the
  coordinates to the end of the list and increment the counter for unique
  points.

  RETURN the number of unique points visited.
*/

public class Main {

    public static void main(String[] args) {

        int x =0;
        int y = 0;
        int total_moves = 0;
        int unique_coords = 1;
        List<String> directions;
        Path path = Paths.get("./input");
        try {
            directions = Files.readAllLines(path);
            Iterator<String> i = directions.iterator();

            while(i.hasNext()) {
                String moves = i.next();

                int[][] houses = new int[moves.length()][2];

                for (int k=0; k < moves.length(); k++) {
                    total_moves++;
                    String floor_move = moves.substring(k,k+1);
                    if (floor_move.equals("^")) {
                        y++;
                    }
                    else if (floor_move.equals("v")) {
                        y--;
                    }
                    else if (floor_move.equals("<")) {
                        x--;
                    }
                    else if (floor_move.equals(">")) {
                        x++;
                    }

                    int[] coords = new int[2];
                    coords[0] = x;
                    coords[1] = y;

                    if (unique(houses, x, y)) {
                        houses[k] = coords;
                        unique_coords++;
                    }

                }

            }
        } catch (IOException e) {
            System.out.println("IOException raised. Did you create the input file?");
            e.printStackTrace();
        }
        System.out.printf("Santa made %d moves and visited %d houses.\n", total_moves, unique_coords);
    }

    public static boolean unique(int[][] houses, int x, int y) {
        for (int j=0; j < houses.length; j++) {
            if (houses[j][0] == x && houses[j][1] == y) {
                return false;
            }
        }
        return true;
    }
}
