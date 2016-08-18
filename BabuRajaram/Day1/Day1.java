public class Day1 {
   public static void main(String[] args) {
      String s=args[0];

     // Part 1
     System.out.println("Final Floor: " + (countFloors(s,'(') - countFloors(s,')')) );

     // Part 2
     for(int i=1; i<s.length(); i++) {
         if (  countFloors(s.substring(0,i),'(') - countFloors(s.substring(0,i),')') == -1 ) {
            System.out.println("Basement reached at position: " + i);
            break;
         }
      }
   }

   public static int countFloors(String s, char c)
   {
      int count=0;
      for(int i=0; i<s.length(); i++) {
         if (s.charAt(i) == c)
            count++;
      }
      return count;
   }
}