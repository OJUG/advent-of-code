import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static String alphabet = "abcdefghijklmnopqrstuvwxyz";

    public static void main(String[] args) {

        // No need for an input file here, we can use CLI args:
        if (args.length < 2) {
            System.out.println("Please specify your puzzle input and iterations.");
            System.out.println("Usage: java Main <puzzle input> <iterations>");
            return;
        }

        String testPass = args[0];
        int iterations  = Integer.parseInt(args[1]);

        for (int i=0; i<iterations; i++) {
            testPass = incrementPassword(testPass);
            while (securityCheck(testPass) == false) {
                testPass = incrementPassword(testPass);
            }
        }
        System.out.printf("Successful password: %s\n", testPass);

    }

    public static boolean securityCheck(String password) {
        return (increasingStraight(password) && niceLetters(password) && twoNonOverlappedPairs(password));
    }

    public static boolean increasingStraight(String password) {
        for (int i=0; i<alphabet.length()-2;i++) {
            String trip = alphabet.substring(i, i+3);
            if (password.indexOf(trip) > -1) {
                return true;
            }
        }
        return false;
    }

    public static boolean niceLetters(String password) {
        Pattern pattern = Pattern.compile("(i|o|l)");
        Matcher matcher = pattern.matcher(password);
        if (matcher.find())
        {
            return false;
        }
        return true;
    }

    public static boolean twoNonOverlappedPairs(String password) {
        String pairs = "";
        for (int i=0; i<password.length()-1; i++) {
            String pair = password.substring(i,i+2);
            if (pair.substring(0,1).equals(pair.substring(1,2))) {
                i++;
                pairs += pair;
            }
        }
        return (pairs.length() >= 4 && !pairs.substring(0,1).equals(pairs.substring(pairs.length()-1,pairs.length())));
    }

    public static String incrementChar(String character, int steps) {
        int index = alphabet.indexOf(character);
        int pos = (doShift(index+steps, alphabet.length())) ? index+steps-alphabet.length() : index+steps;
        return alphabet.substring(pos, pos+1);
    }

    public static boolean doShift(int index, int length) {
        return (index >= length);
    }

    public static String incrementPassword(String password) {
        String newPassword  = "";
        String character    = "";
        String newCharacter = "";
        boolean shift = true;
        for (int i=password.length(); i>0; i--) {
            character = password.substring(i - 1, i);
            if (shift) {
                newCharacter = incrementChar(character, 1);
                shift = doShift(alphabet.indexOf(character) + 1, alphabet.length());
            }
            else {
                newCharacter = character;
            }
            newPassword = newCharacter + newPassword;
        }
        return newPassword;
    }
}
