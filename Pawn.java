import java.util.ArrayList;

/**
 * The pawn chess piece. Contains the calculation for its valid moves.
 * @author Haris Mehuljic
 */
public class Pawn extends ChessPiece {

    /**
     * Creates a new chess piece
     * @param name The name of the piece.
     * @param symbol The symbol to represent the piece on the board printed to the console.
     * @param black Whether or not the piece is black.
     * @param rankPosition The horizontal (x) coordinate of the piece on the board grid.
     * @param filePosition The vertical (y) coordinate of the piece on the board grid.
     */
    public Pawn(boolean black, int rankPosition, int filePosition) {
        super("Pawn", "p", black, rankPosition, filePosition);
    }

    @Override
    public ArrayList<int[]> calculateValidBoardMoves(ChessPiece[][] activePieces) {
        ArrayList<int[]> possibleMoves = new ArrayList<int[]>();
        boolean inStartingPosition = false;
        
        for (int i = 1; i <= 2; i++) {
            int[] moveCoord = new int[2];
            if (black) {
                moveCoord[0] = rankPosition; moveCoord[1] = filePosition - i;
                if (filePosition == 7) {inStartingPosition = true;}
            }
            else {
                moveCoord[0] = rankPosition; moveCoord[1] = filePosition + i;
                if (filePosition == 2) {inStartingPosition = true;}
            }

            // Checking to see if the piece is obstructed
            if (activePieces[moveCoord[0]][moveCoord[1]] == null) {
                // If the piece is anywhere and unobstructed, add the move.
                if (i == 1) {
                    possibleMoves.add(moveCoord);
                    continue;
                }

                // If the piece is in it's starting position, add the move.
                if (inStartingPosition && black && activePieces[moveCoord[0]][moveCoord[1] + 1] == null) {
                    possibleMoves.add(moveCoord);
                }
                else if (inStartingPosition && !black && activePieces[moveCoord[0]][moveCoord[1] - 1] == null) {
                    possibleMoves.add(moveCoord);
                }
            }
        }

        // Checking to see if there's a piece to capture on the diagonals
        for (int i = -1; i <= 1; i += 2) {
            int[] moveCoord = new int[2];

            if (black) {
                moveCoord[0] = rankPosition + i; moveCoord[1] = filePosition - 1;
            }
            else {
                moveCoord[0] = rankPosition + i; moveCoord[1] = filePosition + 1;
            }

            if (moveCoord[0] <= 8 && moveCoord[0] >= 1 && moveCoord[1] <= 8 && moveCoord[1] >= 1) {                   
                if (activePieces[moveCoord[0]][moveCoord[1]] != null && activePieces[moveCoord[0]][moveCoord[1]].isBlack() != this.black) {
                    possibleMoves.add(moveCoord);
                }
            }
        }

        return possibleMoves;
    } 
}
