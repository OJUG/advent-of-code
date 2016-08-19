import java.util.List;
import java.util.ArrayList;
public class Day3 {
   public static void main(String[] args) {
      String s=">^^v^<>v<<<v<v^>>v^^^<v<>^^><^<<^vv>>>^<<....";
      //Part 1
      int numberOfSantas = 1;

      //Part 2
      //int numberOfSantas = 2;

      List<String> visitedHouses = new ArrayList<>();
     
      int x=0, y=0,rx=0, ry=0;
      String temp="";
      visitedHouses.add("0,0");

      for (int i=0; i<s.length(); i++) {
         if (i % numberOfSantas ==0) {
            if(s.charAt(i) == '>') x++;
            if(s.charAt(i) == '<') x--;
            if(s.charAt(i) == '^') y++; 
            if(s.charAt(i) == 'v') y--;
            temp = Integer.valueOf(x) + "," + Integer.valueOf(y);
         }
         else {
            if(s.charAt(i) == '>') rx++;
            if(s.charAt(i) == '<') rx--;
            if(s.charAt(i) == '^') ry++; 
            if(s.charAt(i) == 'v') ry--;
            temp = Integer.valueOf(rx) + "," + Integer.valueOf(ry);
         }

         if (!visitedHouses.contains(temp))
            visitedHouses.add(temp);
      }
      System.out.println("Unique Houses: " + visitedHouses.size());
   }
}

