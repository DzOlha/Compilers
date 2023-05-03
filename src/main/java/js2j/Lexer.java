package js2j;

import js2j.token.LexerToken;
import js2j.token.Token;
import js2j.token.TokenType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Lexer {
    private Map<TokenType, String> regex;
    private List<LexerToken> result;
    public Lexer(){
        result = new ArrayList<>();
        regex = new HashMap<>();
        regex.put(TokenType.LINE_COMMENT, "(//(.*?)[\r$]?\n).*");
        regex.put(TokenType.BLOCK_COMMENT_UNTERMINATED, "(/\\*.*?).*");
        regex.put(TokenType.BLOCK_COMMENT, "(/\\*.*?\\*/).*");
        regex.put(TokenType.WHITE_SPACE, "( ).*");
        regex.put(TokenType.OPENING_BRACE, "(\\().*");
        regex.put(TokenType.CLOSING_BRACE, "(\\)).*");
        regex.put(TokenType.SEMICOLON, "(;).*");
        regex.put(TokenType.COLON, "(:).*");
        regex.put(TokenType.COMMA, "(,).*");
        regex.put(TokenType.OPENING_CURLY_BRACE, "(\\{).*");
        regex.put(TokenType.CLOSING_CURLY_BRACE, "(\\}).*");
        regex.put(TokenType.SCIENTIFIC_LITERAL, "\\b([+\\-]?(?:0|[1-9]\\d*)(?:\\.\\d*)?(?:[eE][+\\-]?\\d+))\\b.*");
        regex.put(TokenType.OCTAL_LITERAL, "\\b(0[0-7]+)\\b.*");
        regex.put(TokenType.DOUBLE_LITERAL, "\\b(\\d{1,9}\\.\\d{1,32})\\b.*");
        regex.put(TokenType.INT_LITERAL, "\\b(\\d{1,9})\\b.*");
        regex.put(TokenType.HEX_LITERAL, "\\b(0[xX][0-9a-fA-F]+)\\b.*");
        regex.put(TokenType.BINARY_LITERAL, "\\b(0[bB][01]+)\\b.*");
        regex.put(TokenType.CHAR, "\\b(char)\\b.*");
        regex.put(TokenType.STRING, "\\b(string)\\b.*");
        regex.put(TokenType.DOUBLE, "\\b(double)\\b.*");
        regex.put(TokenType.BOOLEAN, "\\b(boolean)\\b.*");
        regex.put(TokenType.TAB, "(\\t).*");
        regex.put(TokenType.NEW_LINE, "(\\n).*");
        regex.put(TokenType.POINT, "(\\.).*");
        regex.put(TokenType.ADDITION, "(\\+{1}).*");
        regex.put(TokenType.SUBTRACTION, "(\\-{1}).*");
        regex.put(TokenType.MULTIPLICATION, "(\\*).*");
        regex.put(TokenType.DIVISION, "(/).*");
        regex.put(TokenType.TRIPLE_EQUALS, "(===).*");
        regex.put(TokenType.EQUALS, "(==).*");
        regex.put(TokenType.XOR, "(\\^).*");
        regex.put(TokenType.ASSIGNMENT, "(=).*");
        regex.put(TokenType.TRIPLE_NOT_EQUALS, "(\\!==).*");
        regex.put(TokenType.NOT_EQUALS, "(\\!=).*");
        regex.put(TokenType.MORE, "(>).*");
        regex.put(TokenType.LESS, "(<).*");
        regex.put(TokenType.AND, "(&&).*");
        regex.put(TokenType.OR, "(\\|\\|).*");
        regex.put(TokenType.OPENING_SQUARE_BRACE, "(\\[).*");
        regex.put(TokenType.CLOSING_SQUARE_BRACE, "(\\]).*");
        regex.put(TokenType.STRING_LITERAL,"(\\\"([^\\\\\\\"]|\\\\.)*\\\").*");
        regex.put(TokenType.CHAR_LITERAL, "('(.{1}|\\\\n|\\\\t)').*");
        regex.put(TokenType.IDENTIFIER, "\\b([a-zA-Z]{1}[0-9a-zA-Z_]{0,31})\\b.*");
        regex.put(TokenType.KEYWORD, "\\b(await|break|case|catch|class|const|continue|debugger|default|delete|do|else|enum|export|extends|false|finally|for|function|if|implements|import|in|instanceof|interface|let|new|null|package|private|protected|public|return|super|switch|static|this|throw|try|true|typeof|var|void|while|with|yield)\\b.*");
    }

    public List<Token> tokenize(String source) {
        int position = 0;
        LexerToken token = null;
        do {
            token = this.getNextToken(source, position);
            if (token != null) {
                position = token.getEndPosition();
                this.result.add(token);
            }
        } while (token != null && position != source.length());
        if (position != source.length()) {
           throw new AnalyzerException(source, position, getLineNumber(source, position));
        }
        return this.getFilteredTokens();
    }
    private List<Token> getFilteredTokens() {
        return result.stream()
                .filter(token -> token.isNotAuxiliary())
                .collect(Collectors.toList());
    }
    private LexerToken getNextToken(String source, int from) {
        if (from < 0 || from >= source.length()) {
            throw new Error("Illegal index in the input stream");
        }
        for (Map.Entry<TokenType, String> entry : this.regex.entrySet()) {
            TokenType type = entry.getKey();
            String regex = entry.getValue();
            Pattern pattern = Pattern.compile("^" + regex + "$", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(source.substring(from));
            if (matcher.find()) {
                if (type == TokenType.BLOCK_COMMENT_UNTERMINATED) {
                    throw new AnalyzerException(source, from, getLineNumber(source, from));
                }
                String literal = matcher.group(1);
                int line = getLineNumber(source, from);
                int position = getPositionInLine(source, line, from);
                return new LexerToken(literal, type, line, position, from + literal.length());
            }
        }
        return null;
    }
    private int getLineNumber(String source, int from) {
        return source.substring(0, from + 1).split("\n").length;
    }
    private int getPositionInLine(String source, int line, int from) {
        LineScanner scanner = new LineScanner(source);
        int index = 0;
        int pos = from;
        while (scanner.hasNextLine() && index != line - 1) {
            String skipped = scanner.readNextLine();
            pos -= skipped.length() + 1;
            index++;
        }
        return pos;
    }
}
