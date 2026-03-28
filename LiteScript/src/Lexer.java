import java.util.ArrayList;
import java.util.List;

public class Lexer {

    private final String input;   // The raw text to scan
    private int pos = 0;          // Current position in the text

    public Lexer(String input) {
        this.input = input;
    }

    // Returns the full list of tokens from the input
    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        while (pos < input.length()) {
            char current = input.charAt(pos);

            // Skip whitespace
            if (Character.isWhitespace(current)) {
                pos++;
                continue;
            }

            // Numbers
            if (Character.isDigit(current)) {
                tokens.add(readNumber());
                continue;
            }

            // Identifiers and keywords (if, then, else)
            if (Character.isLetter(current)) {
                tokens.add(readIdentifierOrKeyword());
                continue;
            }

            // Operators and symbols
            switch (current) {
                case '+': tokens.add(new Token(Token.Type.PLUS,     "+")); pos++; break;
                case '-': tokens.add(new Token(Token.Type.MINUS,    "-")); pos++; break;
                case '*': tokens.add(new Token(Token.Type.MULTIPLY, "*")); pos++; break;
                case '/': tokens.add(new Token(Token.Type.DIVIDE,   "/")); pos++; break;
                case '(': tokens.add(new Token(Token.Type.LPAREN,   "(")); pos++; break;
                case ')': tokens.add(new Token(Token.Type.RPAREN,   ")")); pos++; break;
                case '>': tokens.add(new Token(Token.Type.GREATER,  ">")); pos++; break;
                case '<': tokens.add(new Token(Token.Type.LESS,     "<")); pos++; break;
                case '=':
                    // Check if it's == or just =
                    if (pos + 1 < input.length() && input.charAt(pos + 1) == '=') {
                        tokens.add(new Token(Token.Type.EQUALS_EQUALS, "=="));
                        pos += 2;
                    } else {
                        tokens.add(new Token(Token.Type.EQUALS, "="));
                        pos++;
                    }
                    break;
                default:
                    throw new RuntimeException("Unknown character: " + current);
            }
        }

        tokens.add(new Token(Token.Type.EOF, ""));
        return tokens;
    }

    // Reads a full number (handles multi-digit like 123)
    private Token readNumber() {
        StringBuilder sb = new StringBuilder();
        while (pos < input.length() && Character.isDigit(input.charAt(pos))) {
            sb.append(input.charAt(pos));
            pos++;
        }
        return new Token(Token.Type.NUMBER, sb.toString());
    }

    // Reads a full word and checks if it's a keyword
    private Token readIdentifierOrKeyword() {
        StringBuilder sb = new StringBuilder();
        while (pos < input.length() && Character.isLetterOrDigit(input.charAt(pos))) {
            sb.append(input.charAt(pos));
            pos++;
        }
        String word = sb.toString();

        // Check for keywords
        switch (word) {
            case "if":   return new Token(Token.Type.IF,   word);
            case "then": return new Token(Token.Type.THEN, word);
            case "else": return new Token(Token.Type.ELSE, word);
            default:     return new Token(Token.Type.IDENTIFIER, word);
        }
    }
}
