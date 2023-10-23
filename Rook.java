import java.util.ArrayList;

public class Rook extends ChessPiece {

    /**
     * Creates a new chess piece
     * @param name The name of the piece.
     * @param symbol The symbol to represent the piece on the board printed to the console.
     * @param black Whether or not the piece is black.
     * @param rankPosition The horizontal (x) coordinate of the piece on the board grid.
     * @param filePosition The vertical (y) coordinate of the piece on the board grid.
     */
    public Rook(boolean black, int rankPosition, int filePosition) {
        super("Rook", "R", black, rankPosition, filePosition);
    }

    @Override
    public ArrayList<int[]> calculateValidBoardMoves(ChessPiece[][] activePieces) {
        ArrayList<int[]> possibleMoves = new ArrayList<int[]>();

        boolean posVerticalsFound, negVerticalsFound, rightHorizontalsFound, leftHorizontalsFound;
        posVerticalsFound = negVerticalsFound = rightHorizontalsFound = leftHorizontalsFound = false;

        for (int i = 1; i <= 7; i++) {
            int[] posVerticalCoord = {rankPosition, filePosition + i}; int[] negVerticalCoord = {rankPosition, filePosition - i};
            int[] rightHorizontalCoord = {rankPosition + i, filePosition}; int[] leftHorizontalCoord = {rankPosition - i, filePosition};

            if (posVerticalCoord[1] <= 8) { // Checking board coordinates above this piece.
                posVerticalsFound = checkValidityAndAddMove(activePieces, possibleMoves, posVerticalCoord, posVerticalsFound);
            }

            if (rightHorizontalCoord[0] <= 8) { // Checking board coordinates to the right of this piece.
                rightHorizontalsFound = checkValidityAndAddMove(activePieces, possibleMoves, rightHorizontalCoord, rightHorizontalsFound);
            }

            if (negVerticalCoord[1] >= 1) { // Checking board coordinates below this piece.
                negVerticalsFound = checkValidityAndAddMove(activePieces, possibleMoves, negVerticalCoord, negVerticalsFound);
            }

            if (leftHorizontalCoord[0] >= 1) { // Checking board coordinates to the left of this piece.
                leftHorizontalsFound = checkValidityAndAddMove(activePieces, possibleMoves, leftHorizontalCoord, leftHorizontalsFound);
            }
        }

        return possibleMoves;
    }

    /**
     * Checks the if the possible board coordinate is a valid move.
     * @param activePieces Active board pieces in a 2D array.
     * @param possibleMoves The current list of possible moves.
     * @param moveCoord The possible move coordinate that we're checking now.
     * @param correspondingFound The corresponding boolean to check whether all of the possible moves have been found in a row or column.
     * @return True if all the valid moves have been found in a row or column.
     */
    private boolean checkValidityAndAddMove (ChessPiece[][] activePieces, ArrayList<int[]> possibleMoves, int[] moveCoord, boolean correspondingFound) {
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
    
}
