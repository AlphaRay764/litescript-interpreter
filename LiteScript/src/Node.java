public abstract class Node {

    // A number literal e.g. 42
    public static class NumberNode extends Node {
        public final double value;
        public NumberNode(double value) { this.value = value; }
    }

    // A variable name e.g. x
    public static class VariableNode extends Node {
        public final String name;
        public VariableNode(String name) { this.name = name; }
    }

    // A binary operation e.g. 3 + 5
    public static class BinaryOpNode extends Node {
        public final Node left;
        public final String operator;
        public final Node right;
        public BinaryOpNode(Node left, String operator, Node right) {
            this.left = left;
            this.operator = operator;
            this.right = right;
        }
    }

    // A variable assignment e.g. x = 5
    public static class AssignNode extends Node {
        public final String name;
        public final Node value;
        public AssignNode(String name, Node value) {
            this.name = name;
            this.value = value;
        }
    }

    // An if/then/else statement
    public static class IfNode extends Node {
        public final Node condition;
        public final Node thenBranch;
        public final Node elseBranch; // can be null
        public IfNode(Node condition, Node thenBranch, Node elseBranch) {
            this.condition = condition;
            this.thenBranch = thenBranch;
            this.elseBranch = elseBranch;
        }
    }
}