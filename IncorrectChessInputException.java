public class IncorrectChessInputException extends RuntimeException {
    public IncorrectChessInputException (String exceptionMsg) {
        super("Coordinate Input Error: " + exceptionMsg);
    }
}
