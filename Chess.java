import java.util.Scanner;

/**
 * @author Haris Mehuljic
 * A chess game which displays a representation of the chess board in the terminal and
 * takes in user input in order to move and capture pieces. There is no check or checkmate
 * detection.
 */
public class Chess {
    public static void main(String[] args) {
        Board board = new Board();
        Scanner keyboard = new Scanner(System.in);

        boolean gameActive = true;
        boolean isBlacksTurn = false;
        String errorMsg = "";

        while (gameActive) {
            System.out.println(board);

            // If there was an error in the previous play attempt, print it out to the player and erase it.
            if (errorMsg.length() > 0) {
                System.out.println(errorMsg);
                errorMsg = "";
            }

            // Add on whose turn it is to the message
            if(isBlacksTurn) {
                System.out.print("Black's turn. ");
            }
            else {
                System.out.print("White's turn. ");
            }

            System.out.println("Please select a piece by typing its board coordinate with capital letters (i.e. 'E4') or type \"Q\" to quit the game:");

            String selectedCoord = keyboard.nextLine();

            // Quit the game if the player types "Q"
            if (selectedCoord.equals("Q")) {
                keyboard.close();
                gameActive = false;
                break;
            }

            // Try the play; If an error is thrown, it's saved and displayed to the player in the next attempt.
            try {
                board.play(selectedCoord, isBlacksTurn, keyboard);
                isBlacksTurn = !isBlacksTurn;
            }
            catch (IncorrectChessInputException e) {
                errorMsg = e.getMessage();
            }
        }
    }
}