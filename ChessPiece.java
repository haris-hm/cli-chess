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
    public abstract ArrayList<int[]> calculateValidBoardMoves(ChessPiece[][] activePieces);

    /**
     * Checks the if the possible move is a valid move.
     * @param activePieces Active board pieces in a 2D array.
     * @param possibleMoves The current list of possible moves.
     * @param moveCoord The possible move coordinate that we're checking now.
     * @param correspondingFound The corresponding boolean to check whether all of the possible moves have been found in a row or column.
     * @return True if all the valid moves have been found in a row or column.
     */
    protected boolean validatePotentialMove (ChessPiece[][] activePieces, ArrayList<int[]> possibleMoves, int[] moveCoord, boolean correspondingFound) {
        if (activePieces[moveCoord[0]][moveCoord[1]] != null) { // Is there an active piece in the coordinate that we're checking?
            if (activePieces[moveCoord[0]][moveCoord[1]].isBlack() != this.isBlack() && !correspondingFound) { // Is that piece the opposite color? 
                possibleMoves.add(moveCoord); // Add it to the possible moves
                return true; // Stop checking this line
            }
            else if (activePieces[moveCoord[0]][moveCoord[1]].isBlack() == this.isBlack()) { // If it is the same color, stop checking this line without adding the move.
                return true;
            }
        }
        else if (!correspondingFound){ // If the possible coordinate is empty, add it.
            possibleMoves.add(moveCoord);
        }

        return correspondingFound;
    }

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
