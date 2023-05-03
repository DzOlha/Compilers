package js2j.state;

import java.util.HashMap;
import java.util.Map;

public class WordStateFactory {
    public static final int WORD_LIMIT = 5;
    private final Map<StateType, WordState> states;

    public WordStateFactory() {
        states = new HashMap<>(4);
        states.put(StateType.INITIAL, new InitialState());
        states.put(StateType.WORD_BOUND, new WordBoundState());
        states.put(StateType.NO_BOUND, new NoBoundState());
        states.put(StateType.NEWLINE, new NewLineState());
    }

    public Map<StateType, WordState> getStates() {
        return states;
    }

    public static class InitialState extends WordState {
        public StateType next(String ch) {
            return ch.equals("\n") ? StateType.NEWLINE : StateType.WORD_BOUND;
        }
    }

    public static class WordBoundState extends WordState {
        public StateType next(String ch) {
            boolean notWhitespace = !ch.equals(" ");
            StateType next = notWhitespace ? StateType.NO_BOUND : StateType.WORD_BOUND;
            return ch.equals("\n") ? StateType.NEWLINE : next;
        }
    }

    public static class NoBoundState extends WordState {
        public StateType next(String ch) {
            if (ch.equals("\n")) {
                return StateType.NEWLINE;
            }
            if (ch.equals(" ")) {
                whitespaces++;
                return StateType.WORD_BOUND;
            }
            return StateType.NO_BOUND;
        }
    }

    public static class NewLineState extends WordState {
        public StateType next(String ch) {
            return null;
        }
    }
    public void reset() {
        states.forEach((key, value) -> value.close());
    }
}
