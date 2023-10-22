import java.util.ArrayList;
import java.util.Scanner;

public class Board {
    private ArrayList<ChessPiece> activePieces;
    private ArrayList<ChessPiece> lostPieces;

    public Board() {
        activePieces = generateStartingBoard();
        lostPieces = new ArrayList<ChessPiece>();
    }

    /**
     * Generates the starting chess board
     * @return An ArrayList containing all the starting pieces placed at their starting positions
     */
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

    /**
     * A string representation of the chessboard using ASCII box and shading characters
     */
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
     * Selects the piece to be moved.
     * @param isBlack If the current play is for black or white.
     */
    public void play(String input, boolean isBlack, Scanner keyboard) throws IncorrectChessInputException {
        int[] desiredPiece = parseInput(input);
        ChessPiece chosenPiece; 

        for (ChessPiece piece : activePieces) {
            if (piece.getRankPosition() == desiredPiece[0] && piece.getFilePosition() == desiredPiece[1] && piece.isBlack() == isBlack) {
                chosenPiece = piece;
                movePieceToDesiredPos(chosenPiece, keyboard);
            }
            else if (piece.getRankPosition() == desiredPiece[0] && piece.getFilePosition() == desiredPiece[1] && piece.isBlack() != isBlack) {
                throw new IncorrectChessInputException("Desired piece is not your piece!");
            }
        }
    }

    private void movePieceToDesiredPos(ChessPiece chosenPiece, Scanner keyboard) {
        ArrayList<int[]> possibleMoves = chosenPiece.calculateValidBoardMoves(this);

        System.out.println("Here are your possible moves for that chosen piece:");
        System.out.println(possibleMovesRepresentation(possibleMoves));
    }

    private String possibleMovesRepresentation(ArrayList<int[]> moves) {
        String returnStr = "";
        for (int[] move : moves) {
            returnStr += String.format("%d, %d\n", move[0], move[1]);
        }
        return returnStr;
    }

    /**
     * Parses the user input into numerical values.
     * @param coord Takes in the user's inputted coordinate (i.e. 'E4').
     * @return Returns the corresponding numerical value for the input in an integer[] (i.e. [5, 4] for 'E4').
     * @throws IncorrectChessInputException If the input is not in the correct format, this exception is thrown.
     */
    private int[] parseInput(String coord) throws IncorrectChessInputException {
        int[] coordinates = new int[2];

        if (coord.length() > 2) {
            throw new IncorrectChessInputException("Coordinate that was inputted is not in the correct format.");
        }

        try {
            coordinates[0] = coord.charAt(0) - 'A' + 1; // Grabs the first character, and converts it from a letter to its numerical value
            coordinates[1] = Integer.parseInt("" + coord.charAt(1)); // Grabs the second character, which will always be a number
        }
        catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            throw new IncorrectChessInputException("Coordinate that was inputted is not in the correct format.");
        }

        // Checking to see if the letter and the number are in the range of A-H and 1-8
        if (coordinates[0] < 1 || coordinates[0] > 8){
            throw new IncorrectChessInputException("Coordinate that was inputted is out of bounds.");
        }
        else if (coordinates[1] < 1 || coordinates[1] > 8) {
            throw new IncorrectChessInputException("Coordinate that was inputted is out of bounds.");
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
