import java.util.ArrayList;

/**
 * The main class for chess pieces. All piece variants inherit from this.
 * Contains the toString representation for the piece, defines an abstract method
 * for each subclass which is used to calculate valid moves for each variant, and 
 * contains methods for calculating common moves that multiple pieces make.
 * @author Haris Mehuljic
 */
public abstract class ChessPiece {
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
     * @param activePieces The pieces currently on the board placed in a 2D array according to their coordinates on the board.
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
     * Calculates horizontal and vertical movement for rooks and queens.
     * @param activePieces The current active pieces placed in a 2D array according to their board coordinates.
     * @param possibleMoves The piece's current list of possible moves which this method will add to.
     */
    protected void calculateVerticalsAndHorizonatals(ChessPiece[][] activePieces, ArrayList<int[]> possibleMoves) {
        boolean posVerticalsFound, negVerticalsFound, rightHorizontalsFound, leftHorizontalsFound;
        posVerticalsFound = negVerticalsFound = rightHorizontalsFound = leftHorizontalsFound = false;

        // The maximum amount of spaces any piece can move if it is completely unobstructed is 7
        for (int i = 1; i <= 7; i++) {
            // All the possible move coordinates
            int[] posVerticalCoord = {rankPosition, filePosition + i}; int[] negVerticalCoord = {rankPosition, filePosition - i};
            int[] rightHorizontalCoord = {rankPosition + i, filePosition}; int[] leftHorizontalCoord = {rankPosition - i, filePosition};

            if (posVerticalCoord[1] <= 8) { // Checking board coordinates above this piece.
                posVerticalsFound = validatePotentialMove(activePieces, possibleMoves, posVerticalCoord, posVerticalsFound);
            }

            if (rightHorizontalCoord[0] <= 8) { // Checking board coordinates to the right of this piece.
                rightHorizontalsFound = validatePotentialMove(activePieces, possibleMoves, rightHorizontalCoord, rightHorizontalsFound);
            }

            if (negVerticalCoord[1] >= 1) { // Checking board coordinates below this piece.
                negVerticalsFound = validatePotentialMove(activePieces, possibleMoves, negVerticalCoord, negVerticalsFound);
            }

            if (leftHorizontalCoord[0] >= 1) { // Checking board coordinates to the left of this piece.
                leftHorizontalsFound = validatePotentialMove(activePieces, possibleMoves, leftHorizontalCoord, leftHorizontalsFound);
            }
        }
    }

    /**
     * Calculates diagonal movement for bishops and queens.
     * @param activePieces The current active pieces placed in a 2D array according to their board coordinates.
     * @param possibleMoves The piece's current list of possible moves which this method will add to.
     */
    protected void calculateDiagonals(ChessPiece[][] activePieces, ArrayList<int[]> possibleMoves) {
        boolean rightPositivesFound, rightNegativesFound, leftPositivesFound, leftNegativesFound;
        rightPositivesFound = rightNegativesFound = leftPositivesFound = leftNegativesFound = false;

        // The maximum amount of spaces any piece can move if it is completely unobstructed is 7
        for (int i = 1; i <= 7; i++) {
            // All the possible move coordinates
            int[] rightPositiveDiagonal = {rankPosition + i, filePosition + i}; int[] rightNegativeDiagonal = {rankPosition + i, filePosition - i};
            int[] leftPositiveDiagonal = {rankPosition - i, filePosition + i}; int[] leftNegativeDiagonal = {rankPosition - i, filePosition - i};

            if (rightPositiveDiagonal[0] <= 8 && rightPositiveDiagonal[1] <= 8) { // Checking board coordinates on the diagonal going up and to the right.
                rightPositivesFound = validatePotentialMove(activePieces, possibleMoves, rightPositiveDiagonal, rightPositivesFound);
            }

            if (rightNegativeDiagonal[0] <= 8 && rightNegativeDiagonal[1] >= 1) { // Checking board coordinates on the diagonal going down and to the right.
                rightNegativesFound = validatePotentialMove(activePieces, possibleMoves, rightNegativeDiagonal, rightNegativesFound);
            }

            if (leftPositiveDiagonal[0] >= 1 && leftPositiveDiagonal[1] <= 8) { // Checking board coordinates on the diagonal going up and to the left.
                leftPositivesFound = validatePotentialMove(activePieces, possibleMoves, leftPositiveDiagonal, leftPositivesFound);
            }

            if (leftNegativeDiagonal[0] >= 1 && leftNegativeDiagonal[1] >= 1) { // Checking board coordinates on the diagonal going down and to the left.
                leftNegativesFound = validatePotentialMove(activePieces, possibleMoves, leftNegativeDiagonal, leftNegativesFound);
            }
        }
    }

    /**
     * Calculates movement from a set list of moves. Used for the king and knight.
     * @param activePieces The current active pieces placed in a 2D array according to their board coordinates.
     * @param possibleMoves The piece's current list of possible moves which this method will add to.
     * @param validMoves The list of valid moves for the piece.
     */
    protected void calculateCustom(ChessPiece[][] activePieces, ArrayList<int[]> possibleMoves, int[][] validMoves) {
        for (int i = 0; i < validMoves.length; i++) {
            int[] moveCoord = {rankPosition + validMoves[i][0], filePosition + validMoves[i][1]}; // Adds the current valid move to the piece's rank and file position

            if (moveCoord[0] <= 8 && moveCoord[0] >= 1 && moveCoord[1] <= 8 && moveCoord[1] >= 1) { // Is the move that's being checked inside the bounds of the board?
                if (activePieces[moveCoord[0]][moveCoord[1]] != null && activePieces[moveCoord[0]][moveCoord[1]].isBlack() != this.isBlack()) { // Is there a piece currently in the spot that's the opposite color?
                    // Add it to the list of possible moves because it can be captured
                    possibleMoves.add(moveCoord); 
                }
                else if (activePieces[moveCoord[0]][moveCoord[1]] != null && activePieces[moveCoord[0]][moveCoord[1]].isBlack() == this.isBlack()){ // Is there a piece of the same color obstructing the path?
                    continue; // Do nothing
                }

                // Otherwise, add the move
                possibleMoves.add(moveCoord);
            }
        }
    }

    /**
     * @return The symbol of the piece. If the piece is black, it colors its output using ANSI codes, if it's white, it returns just the symbol.
     * @see Used to figure out how to color the text that is output:
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
