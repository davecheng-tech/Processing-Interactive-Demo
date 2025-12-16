import java.util.Scanner;

/**
 * A lightweight implementation of the CodeHS-style ConsoleProgram class.
 * Provides readLine, readInt, readDouble, readBoolean, print, and println helpers.
 */
public class ConsoleProgram {

    private Scanner scanner;

    public ConsoleProgram() {
        scanner = new Scanner(System.in);
    }

    /**
     * Entry point for subclasses. Overridden by student programs.
     */
    public void run() {
        // to be overridden
    }

    // === Universal main() launcher ===
    public static void main(String[] args) {
        try {
            String className;

            if (args.length > 0) {
                // Explicit class name passed as argument (e.g. "Main" or "Circumference")
                className = args[0];
            } else {
                // Fallback: detect the calling class (works when run directly with `java Main`)
                className = new Throwable().getStackTrace()[1].getClassName();
            }

            Class<?> clazz = Class.forName(className);
            ConsoleProgram program = (ConsoleProgram) clazz.getDeclaredConstructor().newInstance();
            program.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // === END Universal main() launcher ===

    // === Input Helpers ===

    public String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public boolean readBoolean(String prompt) {
        while (true) {
            String input = readLine(prompt);
            if (input.equalsIgnoreCase("true")) return true;
            if (input.equalsIgnoreCase("false")) return false;
            System.out.println("Please enter true or false.");
        }
    }

    public double readDouble(String prompt) {
        while (true) {
            String input = readLine(prompt);
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public int readInt(String prompt) {
        while (true) {
            String input = readLine(prompt);
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    // === Output Helpers ===

    public void println() { System.out.println(); }
    public void println(String s) { System.out.println(s); }
    public void println(boolean x) { System.out.println(x); }
    public void println(char x) { System.out.println(x); }
    public void println(char[] x) { System.out.println(x); }
    public void println(int x) { System.out.println(x); }
    public void println(long x) { System.out.println(x); }
    public void println(float x) { System.out.println(x); }
    public void println(double x) { System.out.println(x); }
    public void println(Object o) { System.out.println(o); }

    public void print() { System.out.print(""); }
    public void print(String s) { System.out.print(s); }
    public void print(boolean x) { System.out.print(x); }
    public void print(char x) { System.out.print(x); }
    public void print(char[] x) { System.out.print(x); }
    public void print(int x) { System.out.print(x); }
    public void print(long x) { System.out.print(x); }
    public void print(float x) { System.out.print(x); }
    public void print(double x) { System.out.print(x); }
    public void print(Object o) { System.out.print(o); }
}