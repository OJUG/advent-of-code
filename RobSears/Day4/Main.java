import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
  PROBLEM: Given a random set of characters, determine a numerical suffix such
  that the MD5 hash of whole string is preceded by five zeros.
  STRATEGY: Initialize a counter variable and append it to the provided input.
  Use a while loop to increment the counter, each time appending it to the input
  and calculating the MD5 hash of the result. Evaluate the MD5 hash by comparing
  each of the first five characters to 0 and incrementing a counter each time a
  0 is found. If the MD5 hash is found to contain 5 preceding zeros, break the
  while loop and print the findings.
  RETURN the smallest number that can be appended to the input, so that the MD5
  hash is preceded by 5 (or more) zeros.
*/

public class Main {

    public static void main(String[] args) {

        // No need for an input file here, we can use CLI args:
        if (args.length == 0) {
          System.out.println("Please specify your puzzle input.");
          System.out.println("Usage: java Main 'abcdef'");
          return;
        }

        String message  = args[0];
        String hash = "";
        int counter = 0;

        while (counter > -1) {
            hash = MD5(message + counter);
            if (HashCheck(hash)) {
                break;
            }
            counter++;
        }

        System.out.printf("Answer is %d. The hash of %s is %s.\n", counter, (message+counter), hash);

    }

    /*
      Accepts an MD5 hash and looks at the first five characters.
      Returns true if all five characters are 0.
    */
    public static boolean HashCheck(String hash) {
        int preceding_zeros = 0;
        for (int j=0; j < 5; j++) {
            String chr = hash.substring(j,j+1);
            if (chr.equals("0")) {
                preceding_zeros++;
            }
        }
        return (preceding_zeros == 5);
    }

    /*
      Accepts a string and computes the MD5 hash.
      Returns the MD5 hash of the given string, or "" if the hash can not
      be computed.
    */
    public static String MD5(String message) {
        String hash;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] mdbytes = md.digest(message.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < mdbytes.length; ++i) {
                sb.append(Integer.toHexString((mdbytes[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("No MD5 implementation found.");
            e.printStackTrace();
        }
        return "";
    }
}
