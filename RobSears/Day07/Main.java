import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
    PROBLEM: Given a list of instructions in the format of "operand OPERATOR operand -> output,"
    where the operands can be either 16-bit unsigned integers or strings representing input wire
    labels, and the output is a string representing a wire label, determine the signal carried
    by an arbitrary wire in the circuit.

    NOTES: It is possible for an operand to be null (ex: "(null) NOT 123 -> a", which is actually an
    inversion and not a bitwise NOT. This example evaluates to 65413 for an unsigned 16-bit number).
    It is also possible for both the operator and one operand to be null (ex: "(null) (null) 123 -> a",
    which is simply an input signal to the wire). All wires can only have 1 input. Java doesn't have
    any unsigned types, which needs to be addressed when performing bitwise operations.

    STRATEGY: Locate the instruction that expresses the operands and operator which output to
    the target wire. Recursively trace the operands until one is reached that can be evaluated
    numerically (ie, the instruction is simply an input, like 123 -> a). Then move back a level
    and either trace or evaluate the next operand. The process of associating signals to wires
    will be backed by a Map, which will contain an index of all wire labels that have been
    associated to a signal. This way, the tracing only needs to occur once. Eventually, all
    dependencies of the target wire will be calculated, and the signal to the desired wire can be
    easily determined. This process also avoids tracing paths that do not directly affect the
    signal to the target wire, which reduces processing resources.

    RETURN the signal to the target wire.
 */

 public class Main {

     public static Map<String, Long> wires = new HashMap<String, Long>();
     public static int runs = 0; // Mitigate against runaway recursion.
     public static String override;
     public static Long wire;

     public static void main(String[] args) {

        // User really should pass in the wire labels they want:
        if (args.length != 2) {
          System.out.println("Please specify your puzzle input. First argument is");
          System.out.println("the wire to find, second is the wire to override.");
          System.out.println("Usage: java Main 'a' 'b' ");
          return;
        }
        String wireSeek = args[0];

         // Call the recursive function and print its value to screen:
         Main.wire = recursiveSearch(wireSeek);
         System.out.printf(
                 "Found this for wire '%s': %d. Performed %d recursive searches.\n",
                 wireSeek,
                 Main.wire,
                 Main.runs
         );

         // Reset the simulation state so it can be re-run with new inputs:
         reset();

         // Override the setting for a wire:
         Main.override = args[1];

         // Call the recursive function and print its value to screen:
         Main.wire = recursiveSearch(wireSeek);
         System.out.printf(
                 "Found this for wire '%s': %d. Performed %d recursive searches.\n",
                 wireSeek,
                 Main.wire,
                 Main.runs
         );

     }

     /*
         Given a wire label, search through the input instructions and determine its input(s).
         If the input is itself a label, call the function on that label. Otherwise, add a key
         for the signal in the Map and move on. When both operands are known, perform the
         given operation on them and return the result.
         RETURN the signal to the given wire.
      */
     public static Long recursiveSearch(String operand) {

         Main.runs++;
         if (Main.runs == 1000) {
             System.out.println("Program stopped due to runaway recursion.");
             System.exit(1);
         }
         long value = 0;
         long[] values = new long[2];
         String[] operands = new String[2];
         List<String> instructions;
         Path path = Paths.get("/Users/rob/Projects/Java/Day7/src/com/company/input");
         try {
             instructions = Files.readAllLines(path);
             Iterator<String> i = instructions.iterator();

             while(i.hasNext()) {
                 String instruction = i.next();
                 if (getOutput(instruction).equals(operand)) {
                     operands = getOperands(instruction);
                     for (int j=0; j<operands.length; j++) {
                         if (operands[j] != null) {
                             if (Main.wires.get(operands[j]) == null) {
                                 try {
                                     value = Long.parseLong(operands[j]);
                                     values[j] = value;
                                     Main.wires.put(operand, value);
                                 } catch (NumberFormatException e) {
                                     recursiveSearch(operands[j]);
                                 }
                             }
                         }
                     }
                     String op = getOp(instruction);
                     Long op1 = (Main.wires.get(operands[0]) != null) ? Main.wires.get(operands[0]) : values[0];
                     Long op2 = (Main.wires.get(operands[1]) != null) ? Main.wires.get(operands[1]) : values[1];
                     if (!op.equals("")) {
                         long result = -1;
                         if (op.equals("INV")) {
                             result = 65536+~op1;
                         }
                         if (op.equals("AND")) {
                             result = op1 & op2;
                         }
                         if (op.equals("OR")) {
                             result = op1 | op2;
                         }
                         if (op.equals("LSHIFT")) {
                             result = op1 << op2;
                         }
                         if (op.equals("RSHIFT")) {
                             result = op1 >> op2;
                         }
                         if (op.equals("NOT")) {
                             result = op1 +~ op2;
                         }
                         Main.wires.put(operand, result);
                         return result;
                     }
                     else {
                         if (Main.override != null && Main.override.equals(operand)) {
                             Main.wires.put(operand, Main.wire);
                         } else {
                             Main.wires.put(operand, op1);
                         }
                     }
                 }
             }

         } catch (IOException e) {
             System.out.println("IOException raised. Did you remember to create input file?");
             e.printStackTrace();
         }
         return Main.wires.get(operand);
     }

     /*
         Given an instruction line, use regex to extract the operation that will be performed.
         RETURN the operation to be performed.
      */
     public static String getOp(String instruction) {
         Pattern pattern = Pattern.compile("([A-Z]{1,})");
         Matcher matcher = pattern.matcher(transformInstruction(instruction));
         if (matcher.find())
         {
             return matcher.group(1);
         }
         return "";
     }

     /*
         Given an instruction line, use regex to transform the instruction for clarity.
         For example, a ^NOT is really a binary inverter rather than a bitwise NOT.
         RETURN the transformed instruction.
      */
     public static String transformInstruction(String instruction) {
         return instruction.replaceAll("^NOT", "INV");
     }

     /*
         Given an instruction line, use regex to extract the label of the wire that
         will carry the output signal.
         RETURN the label of the wire to carry the output signal.
      */
     public static String getOutput(String instruction) {
         Pattern pattern = Pattern.compile(".*?-> ([a-z]{1,})");
         Matcher matcher = pattern.matcher(instruction);
         if (matcher.find())
         {
             return matcher.group(1);
         }
         return "";
     }

     /*
         Given an instruction line, get the part of the string that describes the inputs.
         RETURN the input string.
      */
     public static String getInputs(String instruction) {
         String[] lines = instruction.split(" ->");
         return lines[0];
     }

     /*
         Given an instruction line, use regex to determine the labels of the wires carrying
         the input signals (operands).
         RETURN a String array of the operands.
      */
     public static String[] getOperands(String instruction) {
         int i = 0;
         String[] operands = new String[2];
         Pattern pattern = Pattern.compile("[a-z0-9]{1,}");
         Matcher matcher = pattern.matcher(getInputs(instruction));
         while (matcher.find())
         {
             operands[i] = matcher.group();
             i++;
         }
         return operands;
     }

     /*
         Reset the state of the simulation.
      */
     public static void reset() {
         Main.runs = 0;
         Main.wires = new HashMap<String, Long>();
     }

 }
