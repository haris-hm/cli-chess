/**
 * A custom exception for errors that might arise whilst playing the game.
 * @author Haris Mehuljic
 */
public class IncorrectChessInputException extends RuntimeException {
    public IncorrectChessInputException (String exceptionMsg) {
        super("Coordinate Error: " + exceptionMsg);
    }
}
