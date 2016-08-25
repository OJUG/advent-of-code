import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/*
  PROBLEM: Given a list of rectangular dimensions in a known, consistent format,
  calculate the total surface area needed to cover all objects referenced in the
  list.
  STRATEGY: Initialize a variable to keep a running count of the total area.
  Read each set of dimensions from the input, and split into the
  corresponding x,y,z dimensions. Initialize an array consisting of the unique
  areas of each face, then return the area of the smallest face (SAmin).
  Calculate the surface area required as 2LW + 2LH + 2WH + SAmin. Add this to
  the total surface area variable.

  RETURN the final value of the total surface area variable.
*/

public class Main {

    public static void main(String[] args) {

        int total_area = 0;
        int total_ribbon = 0;
        List<String> package_sizes;
        Path path = Paths.get("./input");
        try {
            package_sizes = Files.readAllLines(path);
            Iterator<String> i = package_sizes.iterator();

            while(i.hasNext()) {
                String pkg = i.next();
                String[] pkgsizes = pkg.split("x");
                int length = Integer.parseInt(pkgsizes[0]);
                int width  = Integer.parseInt(pkgsizes[1]);
                int height = Integer.parseInt(pkgsizes[2]);

                List<Integer> areas = Arrays.asList(length*width, width*height, height*length);
                List<Integer> perimeters = Arrays.asList(2*(length+width), 2*(width+height), 2*(height+length));
                int minArea = Collections.min(areas);
                int minPerimeter = Collections.min(perimeters);

                int area = 2*length*width + 2*width*height + 2*height*length + minArea;
                total_area += area;

                int ribbon = minPerimeter + length*width*height;
                total_ribbon += ribbon;

            }
        } catch (IOException e) {
            System.out.println("IOException raised. Did you remember to create input file?");
            e.printStackTrace();
        }

        System.out.printf("Total area: %d, total ribbon: %d\n", total_area, total_ribbon);

    }
}
