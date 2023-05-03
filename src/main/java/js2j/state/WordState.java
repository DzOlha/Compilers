package js2j.state;

public abstract class WordState {
    protected int whitespaces;
    abstract public StateType next(String ch);

    public int getWhitespaces(){
        return whitespaces;
    }

    public void close() {
        whitespaces = 0;
    }
}
