package day1;

/**
 * Created by michaelkolakowski on 8/16/16.
 */
public class Santa {

    public static int goToFloor(String floorSpec) {
        int floor = 0;

        for(int i = 0; i < floorSpec.length(); i++) {
            if(floorSpec.charAt(i) == '(') {
                floor += 1;
            }

            if(floorSpec.charAt(i) == ')') {
                floor -= 1;
            }
        }

        return floor;
    }

    public static int getFirstNegativeIndex(String floorSpec) {
        int floor = 0;

        for(int i = 0; i < floorSpec.length(); i++) {
            if(floorSpec.charAt(i) == '(') {
                floor += 1;
            }

            if(floorSpec.charAt(i) == ')') {
                floor -= 1;
            }

            if(floor < 0) {
                return i + 1;
            }
        }

        return 0;
    }
}
