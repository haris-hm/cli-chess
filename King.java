import java.util.ArrayList;

/**
 * The king chess piece. Contains the calculation for its valid moves.
 * @author Haris Mehuljic
 */
public class King extends ChessPiece {

    /**
     * Creates a new chess piece
     * @param name The name of the piece.
     * @param symbol The symbol to represent the piece on the board printed to the console.
     * @param black Whether or not the piece is black.
     * @param rankPosition The horizontal (x) coordinate of the piece on the board grid.
     * @param filePosition The vertical (y) coordinate of the piece on the board grid.
     */
    public King(boolean black, int rankPosition, int filePosition) {
        super("King", "K", black, rankPosition, filePosition);
    }

    @Override
    public ArrayList<int[]> calculateValidBoardMoves(ChessPiece[][] activePieces) {
        ArrayList<int[]> possibleMoves = new ArrayList<int[]>();

        // The king can only move one space in any direction, as long as it's unobstructed
        int[][] validMoves = {{0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}};
        this.calculateCustom(activePieces, possibleMoves, validMoves);

        return possibleMoves;
    }
    
}
