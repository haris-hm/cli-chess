import java.util.ArrayList;

public abstract class ChessPiece{
    private String name;
    private String symbol;

    protected boolean black;
    protected int rankPosition;
    protected int filePosition;

    /**
     * Creates a new chess piece
     * @param name The name of the piece.
     * @param symbol The symbol to represent the piece on the board printed to the console.
     * @param black Whether or not the piece is black.
     * @param rankPosition The horizontal (x) coordinate of the piece on the board grid.
     * @param filePosition The vertical (y) coordinate of the piece on the board grid.
     */
    public ChessPiece(String name, String symbol, boolean black, int rankPosition, int filePosition) {
        this.name = name;
        this.symbol = symbol;
        this.black = black;
        this.rankPosition = rankPosition;
        this.filePosition = filePosition;
    }

    /**
     * Calculates valid moves for this piece type.
     * @param currentBoard The current playing board.
     * @return An ArrayList of valid moves.
     */
    public abstract ArrayList<int[]> calculateValidBoardMoves(Board currentBoard);

    /**
     * @return The symbol of the piece. If the piece is black, it colors its output using ANSI codes, if it's white, it returns just the symbol.
     * Used to figure out how to color the text that is output:
     * https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
     * https://www.geeksforgeeks.org/how-to-print-colored-text-in-java-console/
     */
    public String toString() {
        if (black) {
            return "\u001B[35m" + symbol + "\u001B[0m"; // "\u001B[35m" is the code for purple, "\u001B[0m" is the reset code
        }
        else {
            return symbol;
        }
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public boolean isBlack() {
        return black;
    }

    public void setBlack(boolean black) {
        this.black = black;
    }

    public int getRankPosition() {
        return rankPosition;
    }

    public int getFilePosition() {
        return filePosition;
    }

    public void movePiece(int rankPosition, int filePosition) {
        this.rankPosition = rankPosition;
        this.filePosition = filePosition;
    }
}
