import java.util.ArrayList;

public class Bishop extends ChessPiece {

    /**
     * Creates a new chess piece
     * @param name The name of the piece.
     * @param symbol The symbol to represent the piece on the board printed to the console.
     * @param black Whether or not the piece is black.
     * @param rankPosition The horizontal (x) coordinate of the piece on the board grid.
     * @param filePosition The vertical (y) coordinate of the piece on the board grid.
     */
    public Bishop(boolean black, int rankPosition, int filePosition) {
        super("Bishop", "B", black, rankPosition, filePosition);
        //TODO Auto-generated constructor stub
    }

    @Override
    public ArrayList<int[]> calculateValidBoardMoves(ChessPiece[][] activePieces) {
        ArrayList<int[]> possibleMoves = new ArrayList<int[]>();

        boolean rightPositivesFound, rightNegativesFound, leftPositivesFound, leftNegativesFound;
        rightPositivesFound = rightNegativesFound = leftPositivesFound = leftNegativesFound = false;

        for (int i = 1; i <= 7; i++) {
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

        return possibleMoves;
    }
    
}
