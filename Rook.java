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

        return possibleMoves;
    }


    
}
