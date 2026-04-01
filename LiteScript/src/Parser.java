// Imports
import java.util.List;

public class Parser {

    private final List<Token> tokens;
    private int pos = 0; // Current position in token list

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    // Look at the current token without consuming it
    private Token peek() {
        return tokens.get(pos);
    }

    // Consume and return the current token
    private Token consume() {
        return tokens.get(pos++);
    }

    // Consume a token and throw an error if it's not the expected type
    private Token expect(Token.Type type) {
        Token t = consume();
        if (t.type != type) {
            throw new RuntimeException("Expected " + type + " but got " + t.type);
        }
        return t;
    }

    // Entry point, parses a full statement
    public Node parse() {
        Node result = parseStatement();
        expect(Token.Type.EOF);
        return result;
    }

    // Statements: assignment or if, otherwise treat as expression
    private Node parseStatement() {
        // Check for assignment: IDENTIFIER = expression
        if (peek().type == Token.Type.IDENTIFIER) {
            Token next = tokens.get(pos + 1);
            if (next.type == Token.Type.EQUALS) {
                String name = consume().value; // consume identifier
                consume();                     // consume '='
                Node value = parseExpression();
                return new Node.AssignNode(name, value);
            }
        }

        // Check for if statement
        if (peek().type == Token.Type.IF) {
            return parseIf();
        }

        return parseExpression();
    }

    // if <condition> then <expr> [else <expr>]
    private Node parseIf() {
        expect(Token.Type.IF);
        Node condition = parseExpression();
        expect(Token.Type.THEN);
        Node thenBranch = parseExpression();

        Node elseBranch = null;
        if (peek().type == Token.Type.ELSE) {
            consume(); // consume 'else'
            elseBranch = parseExpression();
        }

        return new Node.IfNode(condition, thenBranch, elseBranch);
    }

    // Addition and subtraction (lowest precedence)
    private Node parseExpression() {
        Node left = parseTerm();

        while (peek().type == Token.Type.PLUS ||
               peek().type == Token.Type.MINUS ||
               peek().type == Token.Type.EQUALS_EQUALS ||
               peek().type == Token.Type.GREATER ||
               peek().type == Token.Type.LESS) {
            String op = consume().value;
            Node right = parseTerm();
            left = new Node.BinaryOpNode(left, op, right);
        }

        return left;
    }

    // Multiplication and division (higher precedence)
    private Node parseTerm() {
        Node left = parsePrimary();

        while (peek().type == Token.Type.MULTIPLY ||
               peek().type == Token.Type.DIVIDE) {
            String op = consume().value;
            Node right = parsePrimary();
            left = new Node.BinaryOpNode(left, op, right);
        }

        return left;
    }

    // Lowest level: numbers, variables, parentheses
    private Node parsePrimary() {
        Token token = peek();

        if (token.type == Token.Type.NUMBER) {
            consume();
            return new Node.NumberNode(Double.parseDouble(token.value));
        }

        if (token.type == Token.Type.IDENTIFIER) {
            consume();
            return new Node.VariableNode(token.value);
        }

        if (token.type == Token.Type.LPAREN) {
            consume(); // consume '('
            Node expr = parseExpression();
            expect(Token.Type.RPAREN); // consume ')'
            return expr;
        }

        throw new RuntimeException("Unexpected token: " + token);
    }
}
