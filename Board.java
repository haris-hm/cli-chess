import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Board {
    private ArrayList<ChessPiece> activePieces;
    private ArrayList<ChessPiece> lostPieces;

    public Board() {
        activePieces = generateStartingBoard();
        lostPieces = new ArrayList<ChessPiece>();
    }

    private ArrayList<ChessPiece> generateStartingBoard() {
        ArrayList<ChessPiece> board = new ArrayList<ChessPiece>();

        for (int i = 1; i <= 8; i++) {
            board.add(new Pawn(false, i, 2));
            board.add(new Pawn(true, i, 7));

            if (i == 1 || i == 8) {
                board.add(new Rook(false, i, 1));
                board.add(new Rook(true, i, 8));
            }
            else if (i == 2 || i == 7) {
                board.add(new Knight(false, i, 1));
                board.add(new Knight(true, i, 8));
            }
            else if (i == 3 || i == 6) {
                board.add(new Bishop(false, i, 1));
                board.add(new Bishop(true, i, 8));
            }
            else if (i == 4) {
                board.add(new Queen(false, i, 1));
                board.add(new Queen(true, i, 8));
            }
            else if (i == 5) {
                board.add(new King(false, i, 1));
                board.add(new King(true, i, 8));
            }
        }

        return board;
    }

    public String toString() {
        String boardRepresentation = "";
        ChessPiece[][] pieces = new ChessPiece[8][8];

        for (ChessPiece piece : activePieces){
            pieces[8 - piece.getFilePosition()][piece.getRankPosition() - 1] = piece;
        }

        boardRepresentation += " ┌─┬─┬─┬─┬─┬─┬─┬─┐\n";

        for (int i = 0; i < 8; i++) {
            boardRepresentation += 8 - i;

            for (int k = 0; k < 8; k++) {
                if (pieces[i][k] != null){
                    boardRepresentation += "│" + pieces[i][k];
                }
                else {
                    if (i % 2 == 0 && k % 2 == 0) { // When the row and column are both even, add a white background square
                        boardRepresentation += "│█"; 
                    }
                    else if (i % 2 == 0 && k % 2 == 1) { // When the row is even and the column is odd, add a black background square
                        boardRepresentation += "│░"; 
                    }
                    else if (i % 2 == 1 && k % 2 == 0) { // When the row is odd and the column is even, add a black background square
                        boardRepresentation += "│░"; 
                    }
                    else if (i % 2 == 1 && k % 2 == 1) { // When the row and column are both odd, add a white background square
                        boardRepresentation += "│█"; 
                    }
                }                
            }

            boardRepresentation += "│\n";
            if (i != 7) { boardRepresentation += " ├─┼─┼─┼─┼─┼─┼─┼─┤\n"; }
        }

        boardRepresentation += " └─┴─┴─┴─┴─┴─┴─┴─┘\n  a b c d e f g h\n";

        return boardRepresentation;
    }

    /**
     * 
     * @param isBlack
     */
    public void play(String input, boolean isBlack) throws InputMismatchException {
        int[] desiredPiece = parseInput(input);
        ChessPiece chosenPiece;

        for (ChessPiece piece : activePieces) {
            if (piece.getRankPosition() == desiredPiece[0] && piece.getFilePosition() == desiredPiece[1]) {
                chosenPiece = piece;
                System.out.println(chosenPiece.getRankPosition() + " " + chosenPiece.getFilePosition() + " " + chosenPiece);
            }
        }
    }

    /**
     * Parses the user input into numerical values.
     * @param coord Takes in the user's inputted coordinate (i.e. 'E4').
     * @return Returns the corresponding numerical value for the input in an integer[] (i.e. [5, 4] for 'E4').
     */
    private int[] parseInput(String coord) throws InputMismatchException {
        int[] coordinates = new int[2];

        if (coord.length() > 2) {
            throw new InputMismatchException("Coordinate that was inputted is not in the correct format.");
        }

        try {
            coordinates[0] = coord.charAt(0) - 'A' + 1; // Grabs the first character, and converts it from a letter to its numerical value
            coordinates[1] = Integer.parseInt("" + coord.charAt(1)); // Grabs the second character, which will always be a number
        }
        catch (NumberFormatException e) {
            throw new InputMismatchException("Coordinate that was inputted is not in the correct format.");
        }

        // Checking to see if the letter and the number are in the range of A-H and 1-8
        if (coordinates[0] <= 1 || coordinates[0] >= 8){
            throw new InputMismatchException("Coordinate that was inputted is out of bounds.");
        }
        else if (coordinates[1] <= 1 || coordinates[1] >= 8) {
            throw new InputMismatchException("Coordinate that was inputted is out of bounds.");
        }

        return coordinates;
    }

    // Getters and setters    
    public ArrayList<ChessPiece> getActivePieces() {
        return activePieces;
    }

    public void setActivePieces(ArrayList<ChessPiece> activePieces) {
        this.activePieces = activePieces;
    }

    public ArrayList<ChessPiece> getLostPieces() {
        return lostPieces;
    }

    public void setLostPieces(ArrayList<ChessPiece> lostPieces) {
        this.lostPieces = lostPieces;
    }
}
