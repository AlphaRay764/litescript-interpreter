import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Evaluator evaluator = new Evaluator(); // persists variables across inputs

        System.out.println("==========================================");
        System.out.println("   Welcome to LiteScript Interpreter!");
        System.out.println("   Type 'exit' to quit");
        System.out.println("   Type 'vars' to see stored variables");
        System.out.println("==========================================");

        while (true) {
            System.out.print(">> ");
            String input = scanner.nextLine().trim();

            // Exit command
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Goodbye!");
                break;
            }

            // Skip empty input
            if (input.isEmpty()) {
                continue;
            }

            // Show all stored variables
            if (input.equalsIgnoreCase("vars")) {
                if (evaluator.getVariables().isEmpty()) {
                    System.out.println("  No variables stored yet.");
                } else {
                    evaluator.getVariables().forEach((k, v) -> {
                        // Print whole numbers cleanly e.g. 5.0 as 5
                        if (v == Math.floor(v)) {
                            System.out.println("  " + k + " = " + (int) v.doubleValue());
                        } else {
                            System.out.println("  " + k + " = " + v);
                        }
                    });
                }
                continue;
            }

            // Run the input through the pipeline
            try {
                // Step 1: Lex
                Lexer lexer = new Lexer(input);
                List<Token> tokens = lexer.tokenize();

                // Step 2: Parse
                Parser parser = new Parser(tokens);
                Node ast = parser.parse();

                // Step 3: Evaluate
                double result = evaluator.evaluate(ast);

                // Print result cleanly
                if (result == Math.floor(result)) {
                    System.out.println("=> " + (int) result);
                } else {
                    System.out.println("=> " + result);
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }
}