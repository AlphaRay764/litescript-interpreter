// Imports
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class LiteScriptTest {

    private Evaluator evaluator;

    @BeforeEach
    void setup() {
        // Fresh evaluator before each test so variables don't bleed between tests
        evaluator = new Evaluator();
    }

    // Helper method to run a full input through the pipeline and return the result
    private double run(String input) {
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        Parser parser = new Parser(tokens);
        Node ast = parser.parse();
        return evaluator.evaluate(ast);
    }

    // Lexer Tests

    @Test
    void testLexerProducesNumberToken() {
        Lexer lexer = new Lexer("42");
        List<Token> tokens = lexer.tokenize();
        assertEquals(Token.Type.NUMBER, tokens.get(0).type);
        assertEquals("42", tokens.get(0).value);
    }

    @Test
    void testLexerProducesOperatorTokens() {
        Lexer lexer = new Lexer("+ - * /");
        List<Token> tokens = lexer.tokenize();
        assertEquals(Token.Type.PLUS,     tokens.get(0).type);
        assertEquals(Token.Type.MINUS,    tokens.get(1).type);
        assertEquals(Token.Type.MULTIPLY, tokens.get(2).type);
        assertEquals(Token.Type.DIVIDE,   tokens.get(3).type);
    }

    @Test
    void testLexerRecognizesKeywords() {
        Lexer lexer = new Lexer("if then else");
        List<Token> tokens = lexer.tokenize();
        assertEquals(Token.Type.IF,   tokens.get(0).type);
        assertEquals(Token.Type.THEN, tokens.get(1).type);
        assertEquals(Token.Type.ELSE, tokens.get(2).type);
    }

    @Test
    void testLexerDistinguishesEqualsAndEqualsEquals() {
        Lexer lexer = new Lexer("= ==");
        List<Token> tokens = lexer.tokenize();
        assertEquals(Token.Type.EQUALS,        tokens.get(0).type);
        assertEquals(Token.Type.EQUALS_EQUALS, tokens.get(1).type);
    }

    @Test
    void testLexerThrowsOnUnknownCharacter() {
        Lexer lexer = new Lexer("@");
        assertThrows(RuntimeException.class, lexer::tokenize);
    }

    // Evaluator Tests

    @Test
    void testSimpleAddition() {
        assertEquals(8, run("3 + 5"), 0.0001);
    }

    @Test
    void testSimpleSubtraction() {
        assertEquals(4, run("9 - 5"), 0.0001);
    }

    @Test
    void testSimpleMultiplication() {
        assertEquals(12, run("3 * 4"), 0.0001);
    }

    @Test
    void testSimpleDivision() {
        assertEquals(5, run("10 / 2"), 0.0001);
    }

    @Test
    void testOperatorPrecedence() {
        // 3 + 5 * 2 should be 13, not 16
        assertEquals(13, run("3 + 5 * 2"), 0.0001);
    }

    @Test
    void testParenthesesOverridePrecedence() {
        // (3 + 5) * 2 should be 16
        assertEquals(16, run("(3 + 5) * 2"), 0.0001);
    }

    @Test
    void testDivisionByZeroThrows() {
        assertThrows(RuntimeException.class, () -> run("10 / 0"));
    }

    // Variable Tests

    @Test
    void testVariableAssignmentAndRetrieval() {
        run("x = 10");
        assertEquals(10, run("x"), 0.0001);
    }

    @Test
    void testVariableUsedInExpression() {
        run("x = 6");
        assertEquals(12, run("x * 2"), 0.0001);
    }

    @Test
    void testMultipleVariables() {
        run("x = 3");
        run("y = 7");
        assertEquals(10, run("x + y"), 0.0001);
    }

    @Test
    void testUndefinedVariableThrows() {
        assertThrows(RuntimeException.class, () -> run("z"));
    }

    // Conditional Tests

    @Test
    void testIfThenTrueBranch() {
        // 10 > 5 is true (1), so should return 99
        assertEquals(99, run("if 10 > 5 then 99 else 0"), 0.0001);
    }

    @Test
    void testIfThenFalseBranch() {
        // 3 > 5 is false (0), so should return 0
        assertEquals(0, run("if 3 > 5 then 99 else 0"), 0.0001);
    }

    @Test
    void testIfWithEqualsEquals() {
        assertEquals(1, run("if 5 == 5 then 1 else 0"), 0.0001);
    }

    @Test
    void testIfWithNoElseBranchReturnZero() {
        // condition is false and there's no else, should return 0
        assertEquals(0, run("if 1 > 5 then 99"), 0.0001);
    }
}