import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class ProcessAsCallable implements Callable<String> {

    private String chunk;

    public ProcessAsCallable(String chunk) {
        this.chunk = chunk;
    }

    public String call() throws Exception {
        return parseInput(chunk);
    }

    public static String parseInput(String input) {
        String strChar = "";
        int k=0;
        List<String> parsedInput = new ArrayList<String>();
        for (int i=0; i<input.length();i++) {
            if (strChar.equals("") || strChar.equals(input.substring(i,i+1))) {
                strChar = input.substring(i,i+1);
                k++;
            }
            else {
                parsedInput.add(strChar+"-"+k);
                strChar = input.substring(i,i+1);
                k = 1;
            }
        }
        parsedInput.add(strChar+"-"+k);
        return newInput(parsedInput);
    }

    public static String newInput(List<String> parsedInput) {
        String newInput = "";
        for (String chars : parsedInput) {
            String[] pattern = chars.split("-");
            newInput += pattern[1]+pattern[0];
        }
        return newInput;
    }
}
