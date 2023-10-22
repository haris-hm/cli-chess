public class Knight extends ChessPiece {

    /**
     * Creates a new chess piece
     * @param name The name of the piece.
     * @param symbol The symbol to represent the piece on the board printed to the console.
     * @param black Whether or not the piece is black.
     * @param rankPosition The horizontal (x) coordinate of the piece on the board grid.
     * @param filePosition The vertical (y) coordinate of the piece on the board grid.
     */
    public Knight(boolean black, int rankPosition, int filePosition) {
        super("Knight", "N", black, rankPosition, filePosition);
    }

    @Override
    public String calculateValidBoardMoves(Board currentBoard) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculateValidBoardMoves'");
    }
    
}