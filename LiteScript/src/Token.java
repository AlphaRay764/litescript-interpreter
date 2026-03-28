public class Token {

    // All the possible types a token can be
    public enum Type {
        // Literals
        NUMBER, IDENTIFIER,

        // Operators
        PLUS, MINUS, MULTIPLY, DIVIDE,

        // Comparison
        EQUALS, EQUALS_EQUALS, GREATER, LESS,

        // Grouping
        LPAREN, RPAREN,

        // Keywords
        IF, THEN, ELSE,

        // Control
        EOF  // End of input
    }

    public final Type type;    // Kind of token 
    public final String value; // The text it came from

    public Token(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Token(" + type + ", " + value + ")";
    }
}