import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * The main class which controls the board. Stores the current active
 * and captured pieces, generates the starting board, handles moving the
 * pieces during each player's turn, and contains a generator for a String
 * representation of the board to be outputted to the terminal.
 * @author Haris Mehuljic
 */
public class Board {
    private ArrayList<ChessPiece> activePieces;
    private ArrayList<ChessPiece> capturedPieces = new ArrayList<ChessPiece>();

    public Board() {
        activePieces = generateStartingBoard();
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
     * A string representation of the chessboard using simple ASCII box and shading characters
     */
    public String toString() {
        String boardRepresentation = "";
        ChessPiece[][] pieces = new ChessPiece[8][8];

        // Get each piece and put it into the 2D array according to its positions
        for (ChessPiece piece : activePieces){
            /* Because the 2D array goes from top to bottom, and the board coordinates 
             * go from bottom to top, the file and rank positions and the horizontal
             * coordinate are all flipped.
             */
            pieces[8 - piece.getFilePosition()][piece.getRankPosition() - 1] = piece;
        }

        // Effectively clears the terminal.
        for (int i = 0; i < 50; i++) {
            boardRepresentation += "\n";
        }

        boardRepresentation += " ┌─┬─┬─┬─┬─┬─┬─┬─┐\n";

        for (int i = 0; i < 8; i++) {
            boardRepresentation += 8 - i; // Adding the row number for each board row.

            for (int k = 0; k < 8; k++) {
                // If there's a piece in the current position, add it to the board representation.
                if (pieces[i][k] != null){
                    boardRepresentation += "│" + pieces[i][k];
                }
                else { // Generates the classic black & white grid
                    if (i % 2 == 0 && k % 2 == 0) { // When the row and column are both even, add a white background square.
                        boardRepresentation += "│█"; 
                    }
                    else if (i % 2 == 0 && k % 2 == 1) { // When the row is even and the column is odd, add a black background square.
                        boardRepresentation += "│░"; 
                    }
                    else if (i % 2 == 1 && k % 2 == 0) { // When the row is odd and the column is even, add a black background square.
                        boardRepresentation += "│░"; 
                    }
                    else if (i % 2 == 1 && k % 2 == 1) { // When the row and column are both odd, add a white background square.
                        boardRepresentation += "│█"; 
                    }
                }                
            }

            // Capping each row and adding the bottom grid to them.
            boardRepresentation += "│\n";
            if (i != 7) { boardRepresentation += " ├─┼─┼─┼─┼─┼─┼─┼─┤\n"; }
        }

        // Adding the bottom to the board grid and the letters representing columns.
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
        boolean playDone = false;

        for (ChessPiece piece : activePieces) {
            // Checks if the current piece is in the position of the desired piece and if it's the current player's color.
            if (piece.getRankPosition() == desiredPiece[0] && piece.getFilePosition() == desiredPiece[1] && piece.isBlack() == isBlack) {
                chosenPiece = piece;
                movePieceToDesiredPos(chosenPiece, keyboard);
                playDone = true;
                break;
            } // If the piece is the opposite color, output an error to the player.
            else if (piece.getRankPosition() == desiredPiece[0] && piece.getFilePosition() == desiredPiece[1] && piece.isBlack() != isBlack) {
                throw new IncorrectChessInputException("Desired piece is not your piece!");
            }
        }

        if (!playDone) {
            throw new IncorrectChessInputException("There isn't a piece in that position, try again.");
        }
    }

    /**
     * Moves the piece to the specified position and captures pieces
     * @param chosenPiece The piece to be moved.
     * @param keyboard The Scanner object currently being used.
     * @throws IncorrectChessInputException An error if the selected piece doesn't have any available moves or if an invalid move is picked.
     */
    private void movePieceToDesiredPos(ChessPiece chosenPiece, Scanner keyboard) throws IncorrectChessInputException {
        ChessPiece[][] piecesInPosition = activePieceArray();
        ArrayList<int[]> possibleMoves = chosenPiece.calculateValidBoardMoves(piecesInPosition);
        boolean validMoveChosen = false;

        // Checks to see if the piece has any valid moves available. If not, it throws an error.
        if (possibleMoves.size() == 0) {
            throw new IncorrectChessInputException("That piece does not have any possible moves. Please select another piece.");
        }

        // Keeps looping as long as a valid move has not been chosen
        while(!validMoveChosen) {
            System.out.println(possibleMovesRepresentation(possibleMoves));
            int[] chosenMove = parseInput(keyboard.nextLine());
            
            // Loop through all the possible moves and check if the chosen move is one of them
            for (int[] move : possibleMoves) {
                if (Arrays.equals(move, chosenMove)) {
                    // If it is, move the piece
                    validMoveChosen = true;
                    chosenPiece.movePiece(chosenMove[0], chosenMove[1]);

                    // If there is a piece in that spot, capture it and remove it from the active pieces.
                    if (piecesInPosition[move[0]][move[1]] != null) {
                        activePieces.remove(piecesInPosition[move[0]][move[1]]);
                        capturedPieces.add(piecesInPosition[move[0]][move[1]]);
                    }

                    break;
                }
            }

            // Tells the player to choose a valid move if the current choice was determined invalid.
            if (!validMoveChosen) {
                System.out.println("That was not a valid move. Please choose a valid move according to the board below.");
            }
        }

    }

    /**
     * Generates a representation of the board with only the squares that are possible moves 
     * for that piece are highlighted. Most of the code is very similar to the Board toString().
     * @param moves The list of possible moves for the piece.
     * @return The representation of the board.
     */
    private String possibleMovesRepresentation(ArrayList<int[]> moves) {
        boolean[][] symbolPositions = new boolean[8][8];
        String boardRepresentation = "Here are your possible moves:\n";

        for (int[] move : moves) {
            // Flipping coordinates the same way it was done in the toString() method
            symbolPositions[8 - move[1]][move[0] - 1] = true;
        }

        boardRepresentation += " ┌─┬─┬─┬─┬─┬─┬─┬─┐\n";

        for (int i = 0; i < 8; i++) {
            boardRepresentation += 8 - i; // Adding the row number to the left hand side

            for (int k = 0; k < 8; k++) {
                if (symbolPositions[i][k]) {
                    boardRepresentation += "│█";
                }
                else {
                    boardRepresentation += "│ ";
                }   
            }

            boardRepresentation += "│\n";
            if (i != 7) { boardRepresentation += " ├─┼─┼─┼─┼─┼─┼─┼─┤\n"; }
        }

        boardRepresentation += " └─┴─┴─┴─┴─┴─┴─┴─┘\n  a b c d e f g h\n Please type the coordinates of the move you would like to make.";       

        return boardRepresentation;
    }

    /**
     * Generates a 2D array with chess pieces placed according to their board positions.
     * @return The 2D array.
     */
    private ChessPiece[][] activePieceArray() {
        ChessPiece[][] piecesInPosition = new ChessPiece[9][9];
        for (ChessPiece piece : this.getActivePieces()) {
            piecesInPosition[piece.getRankPosition()][piece.getFilePosition()] = piece;
        }
        return piecesInPosition;
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
            coordinates[0] = coord.charAt(0) - 'A' + 1; // Grabs the first character, and converts it from a letter to its numerical value.
            coordinates[1] = Integer.parseInt("" + coord.charAt(1)); // Grabs the second character, which will always be a number.
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

    public ArrayList<ChessPiece> getCapturedPieces() {
        return capturedPieces;
    }

    public void setCapturedPieces(ArrayList<ChessPiece> lostPieces) {
        this.capturedPieces = lostPieces;
    }
}
