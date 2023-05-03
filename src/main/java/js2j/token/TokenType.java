package js2j.token;

public enum TokenType {
    BLOCK_COMMENT,
    BLOCK_COMMENT_UNTERMINATED,
    LINE_COMMENT,
    WHITE_SPACE,
    TAB,
    NEW_LINE,
    CLOSING_BRACE,
    OPENING_BRACE,
    OPENING_CURLY_BRACE,
    CLOSING_CURLY_BRACE,
    SCIENTIFIC_LITERAL,
    OCTAL_LITERAL,
    DOUBLE_LITERAL,
    INT_LITERAL,
    HEX_LITERAL,
    BINARY_LITERAL,
    ADDITION,
    SUBTRACTION,
    MULTIPLICATION,
    DIVISION,
    XOR,
    POINT,
    EQUALS,
    TRIPLE_EQUALS,
    NOT_EQUALS,
    TRIPLE_NOT_EQUALS,
    ASSIGNMENT,
    MORE,
    LESS,
    AND,
    OR,
    INT,
    CHAR,
    STRING,
    DOUBLE,
    BOOLEAN,
    SEMICOLON,
    COLON,
    COMMA,
    IDENTIFIER,
    KEYWORD,
    OPENING_SQUARE_BRACE,
    CLOSING_SQUARE_BRACE,
    STRING_LITERAL,
    CHAR_LITERAL;

    public boolean isTokenAuxiliary()  {
        return (
                //this == BLOCK_COMMENT ||
               //this == LINE_COMMENT ||
                this == NEW_LINE ||
                this == TAB ||
                this == WHITE_SPACE
        );
    }
}
