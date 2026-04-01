// Imports
import java.util.HashMap;
import java.util.Map;

public class Evaluator {

    // Stores variables and their values 
    private final Map<String, Double> variables = new HashMap<>();

    // Main entry point and evaluates any node
    public double evaluate(Node node) {

        // A plain number, just returns it
        if (node instanceof Node.NumberNode) {
            return ((Node.NumberNode) node).value;
        }

        // A variable to look up in the map
        if (node instanceof Node.VariableNode) {
            String name = ((Node.VariableNode) node).name;
            if (!variables.containsKey(name)) {
                throw new RuntimeException("Undefined variable: " + name);
            }
            return variables.get(name);
        }

        // An assignment, evaluates the value and stores it
        if (node instanceof Node.AssignNode) {
            Node.AssignNode assign = (Node.AssignNode) node;
            double value = evaluate(assign.value);
            variables.put(assign.name, value);
            return value;
        }

        // Binary operation, evaluate both sides then apply the operator
        if (node instanceof Node.BinaryOpNode) {
            Node.BinaryOpNode binOp = (Node.BinaryOpNode) node;
            double left  = evaluate(binOp.left);
            double right = evaluate(binOp.right);

            switch (binOp.operator) {
                case "+":  return left + right;
                case "-":  return left - right;
                case "*":  return left * right;
                case "/":
                    if (right == 0) throw new RuntimeException("Error: Division by zero");
                    return left / right;
                case "==": return left == right ? 1 : 0;
                case ">":  return left >  right ? 1 : 0;
                case "<":  return left <  right ? 1 : 0;
                default:
                    throw new RuntimeException("Unknown operator: " + binOp.operator);
            }
        }

        // An if/then/else, evaluate condition, then pick a branch
        if (node instanceof Node.IfNode) {
            Node.IfNode ifNode = (Node.IfNode) node;
            double condition = evaluate(ifNode.condition);

            if (condition != 0) {
                return evaluate(ifNode.thenBranch);
            } else if (ifNode.elseBranch != null) {
                return evaluate(ifNode.elseBranch);
            } else {
                return 0; // no else branch, return 0 by default
            }
        }

        throw new RuntimeException("Unknown node type: " + node.getClass());
    }

    // REPL (Read, evaluate, print, loop) displays all stored variables
    public Map<String, Double> getVariables() {
        return variables;
    }
}
