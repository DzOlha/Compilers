package js2j.token;

public class LexerToken extends Token {
    private int to;
    public LexerToken(String literal, TokenType type, int line, int position, int to) {
        super(literal, type, line, position);
        this.to = to;
    }
    public int getEndPosition() {
        return this.to;
    }
    public boolean isNotAuxiliary() {
        return !this.type.isTokenAuxiliary();
    }
    public String toString() {
        if (this.isNotAuxiliary()) {
            return this.type + " '" + this.literal.trim() + "' [L" + this.line + ":" + this.position + "]";
        }
        return this.type + " [L" + this.line + ":" + this.position + "]";
    }
}
