package js2j.state;

public enum StateType {
    INITIAL,// default state of the lexer, where it starts processing the input
    WORD_BOUND,// when the lexer encounters a word boundary (whitespace)
    NO_BOUND,// when the lexer is within a token and there are no word boundaries
    NEWLINE;// when the lexer encounters a newline character
}
