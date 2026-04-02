# LiteScript Interpreter

A mini programming language interpreter built in Java, capable of evaluating mathematical expressions, storing variables, and executing conditional logic. Implements the three core stages of a real interpreter: **lexing**, **parsing**, and **evaluation**.

## Features

- Evaluate mathematical expressions with correct operator precedence
- Parentheses support for grouping expressions
- Variable assignment and retrieval
- Comparison operators (`==`, `>`, `<`)
- Conditional `if / then / else` statements
- Interactive REPL (Read-Eval-Print Loop) shell
- Unit tests covering functionality


## How to Use

1. Clone the repository
2. Navigate to the source folder
3. Run the interpreter (Main.java file)

## Example Usage
```
==========================================
   Welcome to LiteScript Interpreter!
   Type 'exit' to quit
   Type 'vars' to see stored variables
==========================================
>> 3 + 5 * 2
=> 13
>> (3 + 5) * 2
=> 16
>> x = 10
=> 10
>> y = x + 5
=> 15
>> vars
  x = 10
  y = 15
>> if x > 5 then 99 else 0
=> 99
>> exit
Goodbye!
```

## Project Structure
```
litescript-interpreter/
├── LiteScript/
│   └── src/
│       ├── Token.java          # Defines all token types and the Token object
│       ├── Lexer.java          # Scans raw input and produces a list of tokens
│       ├── Node.java           # AST node types (numbers, variables, operations, conditionals)
│       ├── Parser.java         # Builds an Abstract Syntax Tree from tokens
│       ├── Evaluator.java      # Walks the AST and computes results
│       ├── Main.java           # REPL entry point, ties everything together
│       └── LiteScriptTest.java # Unit tests
└── README.md
```

## How to Run Tests

1. Open the project in VS Code
2. Click the testing icon in the left sidebar 
3. Click "Run All Tests"

All 20 tests should pass, covering lexing, expression evaluation, operator precedence, variable assignment, and conditional logic.

## Concepts Demonstrated

- **Lexical Analysis** — scanning raw text into a stream of typed tokens
- **Recursive Descent Parsing** — building an AST that correctly encodes operator precedence without any libraries
- **Tree Evaluation** — recursively walking an AST to compute results
- **Symbol Table** — using a HashMap to store and retrieve variables across expressions
- **REPL Design** — building an interactive shell that maintains state across inputs
- **Unit Testing** — writing structured tests with JUnit covering functionality and error cases

*Built as a portfolio project*