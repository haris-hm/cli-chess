import java.util.ArrayList;

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
    public ArrayList<int[]> calculateValidBoardMoves(Board currentBoard) {
        ArrayList<int[]> possibleMoves = new ArrayList<int[]>();
        ChessPiece[][] activePieces = new ChessPiece[9][9];

        int[][] validMoves = {{1, 2}, {2, 1}, {2, -1}, {1, -2}, {-1, -2}, {-2, -1}, {-2, 1}, {-1, 2}};

        for (ChessPiece piece : currentBoard.getActivePieces()) {
            activePieces[piece.getRankPosition()][piece.getFilePosition()] = piece;
        }

        for (int i = 0; i < 8; i++) {
            int[] moveCoord = {rankPosition + validMoves[i][0], filePosition + validMoves[i][1]};

            if (moveCoord[0] <= 8 && moveCoord[0] >= 1 && moveCoord[1] <= 8 && moveCoord[1] >= 1) {
                if (activePieces[moveCoord[0]][moveCoord[1]] != null && activePieces[moveCoord[0]][moveCoord[1]].isBlack() != this.isBlack()) {
                    possibleMoves.add(moveCoord);
                }
                else if (activePieces[moveCoord[0]][moveCoord[1]] != null && activePieces[moveCoord[0]][moveCoord[1]].isBlack() == this.isBlack()){
                    continue;
                }

                possibleMoves.add(moveCoord);
            }
        }

        return possibleMoves;
    }
    
}
