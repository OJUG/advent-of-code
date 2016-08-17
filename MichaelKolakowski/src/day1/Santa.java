package day1;

/**
 * Created by michaelkolakowski on 8/16/16.
 */
public class Santa {

    private static final int GO_UP_ONE_FLOOR = 1;
    private static final int GO_DOWN_ONE_FLOOR = -1;
    private static final int DO_NOT_MOVE = 0;

    public static int goToFloor(String floorSpec) {
        int floor = 0;

        for(int i = 0; i < floorSpec.length(); i++) {
            floor += adjustFloor( floorSpec.charAt(i) );
        }

        return floor;
    }

    public static int getFirstNegativeIndex(String floorSpec) {
        int floor = 0;

        for(int i = 0; i < floorSpec.length(); i++) {
            floor += adjustFloor( floorSpec.charAt(i) );

            if(floor < 0) {
                return i + 1;
            }
        }

        return 0;
    }

    private static int adjustFloor(char floorSymbol) {
        if( floorSymbol == '(' ) {
            return GO_UP_ONE_FLOOR;
        } else if( floorSymbol == ')' ) {
            return GO_DOWN_ONE_FLOOR;
        } else {
            return DO_NOT_MOVE;
        }
    }
}
