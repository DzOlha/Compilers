package js2j;

public class LineScanner {
    private int position = 0;
    private String[] lines = {};

    public LineScanner(String source) {
        this.lines = source.split("\n");
    }

    public boolean hasNextLine() {
        return this.position < this.lines.length;
    }

    public String readNextLine() {
        return this.lines[this.position++];
    }
}
