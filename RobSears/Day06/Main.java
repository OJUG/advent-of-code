import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static int lightsOn    = 0;
    public static int lightBright = 0;

    public static void main(String[] args) {

        int[][] planeLight  = new int[1000][1000];
        int[][] planeBright = new int[1000][1000];

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
                            planeBright[x][y] += 1;
                            planeLight[x][y]   = 1;
                        } else if (instruction.contains("turn off")) {
                            planeBright[x][y] -= (planeBright[x][y] > 0) ? 1 : 0;
                            planeLight[x][y]   = 0;
                        } else if (instruction.contains("toggle")) {
                            planeBright[x][y] += 2;
                            planeLight[x][y]   = (planeLight[x][y] == 0) ? 1 : 0;
                        }
                    }
                }
            }

            countLights(planeLight, planeBright);
            System.out.printf("There are %d lights on, and %d total brightness.\n", Main.lightsOn, Main.lightBright);

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
        int[] coord = new int[4];
        Pattern pattern = Pattern.compile("([0-9]{1,}),([0-9]{1,}).*?([0-9]{1,}),([0-9]{1,})");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find())
        {
            coord[0] = Integer.parseInt(matcher.group(1));
            coord[1] = Integer.parseInt(matcher.group(3));
            coord[2] = Integer.parseInt(matcher.group(2));
            coord[3] = Integer.parseInt(matcher.group(4));
        }
        return coord;
    }

    /*
        Iterate over the entire plane and count the coordinates that are equal to 1.
        Return the total number of coordinates that equal 1.
     */
    public static void countLights(int[][] planeLight, int[][] planeBright) {
        for (int i=0; i<1000; i++) {
            for (int j=0; j<1000; j++) {
                if (planeBright[i][j] > 0) {
                    Main.lightBright += planeBright[i][j];
                }
                if (planeLight[i][j] == 1) {
                    Main.lightsOn++;
                }
            }
        }
    }
}
