package js2j.token;

public abstract class Token {
    protected String literal;
    protected TokenType type;
    protected int line;
    protected int position;

    protected Token(String literal, TokenType type, int line, int position) {
        this.literal = literal;
        this.type = type;
        this.line = line;
        this.position = position;
    }
    public String getLiteral() {
        return literal;
    }
    public int getLine() {
        return line;
    }
}
