package js2j;

import js2j.state.StateType;
import js2j.state.WordState;
import js2j.state.WordStateFactory;

public class AnalyzerException extends Error{
    private WordStateFactory wordState = new WordStateFactory();
    private String message;

    public AnalyzerException(String source, int position, int line) {
        String substring = source.substring(
                position - this.getStartOffsetIndex(source, position),
                position + this.getEndOffsetIndex(source, position)
        ).trim();
        this.message = "You have an error in your syntax near ";
        this.printErrorMessage(substring, line, position);
    }

    private void printErrorMessage(String message, int line, int position) {
        System.out.println("\n" + this.message + "\n--------------------------------------------------------------------------------->");
        System.out.println("LINE: " + line + " POSITION: " + position + "  ------------------->  " + message + "  ");
        System.out.println("--------------------------------------------------------------------------------->  \n");
    }
    private int getStartOffsetIndex(String source, int position) {
        int index = 0;
        WordState state = wordState.getStates().get(StateType.INITIAL);

        while (position - index != 0 && state != null &&
                state.getWhitespaces() != wordState.WORD_LIMIT &&
                state != wordState.getStates().get(StateType.NEWLINE)) {
            index++;

            String ch = Character.toString(source.charAt(position + index));
            StateType property = state.next(ch);

            if (property != null) {
                state = wordState.getStates().get(property);
            }
        }
        wordState.reset();
        return index;
    }

    private int getEndOffsetIndex(String source, int position) {
        int index = 0;
        WordState state = wordState.getStates().get(StateType.INITIAL);
        while (
                position + index != source.length() - 1 &&
                        state.getWhitespaces() != wordState.WORD_LIMIT &&
                        state != wordState.getStates().get(StateType.NEWLINE)
        ) {
            index++;

            String ch = Character.toString(source.charAt(position + index));
            StateType property = state.next(ch);

            if (property != null) {
                state = wordState.getStates().get(property);
            }
        }
        wordState.reset();
        return index;
    }
}
