import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/*
    PROBLEM: Given a string of digits, create a new string that expresses the
    previous string in terms of how many times a digit occurs. Repeat an
    arbitrary number of times, each time using the new string as an input
    to be processed. This is known as a look and say sequence.

    STRATEGY: The compute time required to process an input X times also grows
    exponentially. To handle this, we will employ a strategy of breaking the
    input into "chunks" and use Java's Callable library to process each chunk
    and return the processed result. This strategy is only possible if A) the
    splits occur at boundaries (ie, between two different digits), and B) if
    the processed chunks are concatenated in order.

    We'll extract each digit from the given chunk. If the digit is at the start
    of the chunk or is a repeat digit, increment a counter. Otherwise,
    record the digit and value of the counter to an ArrayList, then reset the
    counter and keep going. This ArrayList can be used to generate a new string
    representing the look and say sequence for the previous string.

    RETURNS the length of the final string.
*/

public class Main {

    // A large threadpool speeds up processing for turns >~45
    public static int threadPool = 500;

    public static void main(String[] args)  throws InterruptedException, ExecutionException {

        // No need for an input file here, we can use CLI args:
       if (args.length == 0) {
           System.out.println("Please specify your puzzle input.");
           System.out.println("Usage: java Main <input> <turns>");
           System.out.println("Example: java Main 112233 40");
           return;
       }

       String input  = args[0];
       int turns  = Integer.parseInt(args[1]);

        System.out.printf("Input: %s.\n", input);
        for (int q=0; q<turns; q++) {
            ExecutorService executorService = Executors.newFixedThreadPool(Main.threadPool);
            List<Callable<String>> lst = new ArrayList<Callable<String>>();
            for (String part : divideInput(input)) {
                if (!part.equals("")) {
                    lst.add(new ProcessAsCallable(part));
                }
            }
            List<Future<String>> tasks = executorService.invokeAll(lst);
            input = "";
            for (Future<String> task : tasks) {
                input += task.get();
            }
            executorService.shutdown();
        }
        System.out.printf("Output is %d characters long.\n", input.length());
    }

    /*
        Given an input, break it up into chunks based on the threadpool settings.
        Note that chunking must occur on a digit boundary, ie, between a 1 and 2,
        or 2 and 3, otherwise the answer will be wrong.
        RETURNS a String array of all chunks.
     */
    public static String[] divideInput(String input) {
        int chunks = Main.threadPool;
        int blockSize = (int) Math.ceil(((double)input.length() / Main.threadPool));
        String[] parts = new String[chunks];
        int start = 0;
        int end=0;
        int length = (blockSize < input.length()) ? blockSize : input.length();
        for (int i=0; i<chunks; i++) {
            if (input.length() > start + length) {
                // This ensures that chunking occurs between different digits:
                for (int j = start + length - 1; j < input.length() - 1; j++) {
                    if (!input.substring(j, j + 1).equals(input.substring(j + 1, j + 2))) {
                        end = j + 1;
                        break;
                    }
                }
            }
            else {
                end = input.length();
            }
            parts[i] = input.substring(start, end);
            start = end;
        }
        return parts;
    }

}
