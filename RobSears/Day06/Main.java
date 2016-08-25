import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {

        int[][] plane = new int[1000][1000];
        List<String> instructions;
        Path path = Paths.get("./input");
        try {
            instructions = Files.readAllLines(path);
            Iterator<String> i = instructions.iterator();

            while(i.hasNext()) {
                String instruction = i.next();

                int[] coords = fetchCoords(instruction);

                for (int x=coords[0]; x<=coords[1]; x++) {
                    for (int y=coords[2]; y<=coords[3]; y++) {
                        if (instruction.contains("turn on")) {
                            plane[x][y] = 1;
                        } else if (instruction.contains("turn off")) {
                            plane[x][y] = 0;
                        } else if (instruction.contains("toggle")) {
                            plane[x][y] = (plane[x][y] == 0) ? 1 : 0;
                        }
                    }
                }
            }

            int lightsOn = countLights(plane);
            System.out.printf("There are %d lights on.\n", lightsOn);

        } catch (IOException e) {
            System.out.println("IOException raised. Did you remember to create input file?");
            e.printStackTrace();
        }

    }

    /*
        Given a string with two coordinate pairs, extract the X and Y locations.
        Return an int array in the format [x1,x2,y1,y2]
     */
    public static int[] fetchCoords(String line) {
        int[] coords = new int[4];
        Pattern pattern = Pattern.compile("([0-9]{1,}),([0-9]{1,}).*?([0-9]{1,}),([0-9]{1,})");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find())
        {
            coords[0] = Integer.parseInt(matcher.group(1));
            coords[1] = Integer.parseInt(matcher.group(3));
            coords[2] = Integer.parseInt(matcher.group(2));
            coords[3] = Integer.parseInt(matcher.group(4));
        }
        return coords;
    }

    /*
        Iterate over the entire plane and count the coordinates that are equal to 1.
        Return the total number of coordinates that equal 1.
     */
    public static int countLights(int[][] plane) {
        int lightsOn = 0;
        for (int i=0; i<1000; i++) {
            for (int j=0; j<1000; j++) {
                if (plane[i][j] == 1) {
                    lightsOn++;
                }
            }
        }
        return lightsOn;
    }
}
