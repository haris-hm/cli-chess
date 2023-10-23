import java.util.ArrayList;

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
        
        for (int i = 1; i <= 2; i++) {
            int[] coords = new int[2];
            if (black) {
                coords[0] = rankPosition; coords[1] = filePosition - i;
            }
            else {
                coords[0] = rankPosition; coords[1] = filePosition + i;
            }

            // If the pawn wouldn't go out of bounds or isn't blocked by another piece, continue
            if (coords[1] < 8 && coords[1] > 1 && activePieces[coords[0]][coords[1]] == null) {
                // If the possible move is adding 2 or removing 2 from the file position
                if(coords[1] == filePosition + 2 || coords[1] == filePosition - 2) {
                    // Add the possible move only if the piece is at its starting position
                    if (filePosition == 7 && black) {
                        possibleMoves.add(coords);
                    }
                    else if (filePosition == 2 && !black) {
                        possibleMoves.add(coords);
                    }
                    else {
                        break;
                    }
                }
                else { // If the move isn't adding or removing 2
                    possibleMoves.add(coords);
                }
            }
        }

        // Checking to see if there's a piece to capture on the diagonals
        for (int i = -1; i <= 1; i += 2) {
            int[] coords = new int[2];
            if (black) {
                coords[0] = rankPosition + i; coords[1] = filePosition - 1;
                if (activePieces[coords[0]][coords[1]] != null && !activePieces[coords[0]][coords[1]].isBlack()) {
                    possibleMoves.add(coords);
                }
            }
            else {
                coords[0] = rankPosition + i; coords[1] = filePosition + 1;
                if (activePieces[coords[0]][coords[1]] != null && activePieces[coords[0]][coords[1]].isBlack()) {
                    possibleMoves.add(coords);
                }
            }
        }

        return possibleMoves;
    }
    
}
